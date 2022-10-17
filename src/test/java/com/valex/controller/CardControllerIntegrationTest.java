package com.valex.controller;

import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.mother.CardMother.getCardRequest;
import static com.valex.domain.mother.UserMother.getUserRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.Factory.CardFactory;
import com.valex.Factory.UserFactory;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.mother.CardMother;
import com.valex.domain.request.CardRequest;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import com.valex.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Sql(value = "/clean-database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class CardControllerIntegrationTest {

  private static final String BASE_URL = "/cards";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserFactory userFactory;

  @Autowired
  private CardFactory cardFactory;

  @Test
  @WithMockUser
  void givenCreateCardThenReturnCreatedCard () throws Exception {
    UserResponse user = userFactory.createUserInTheDatabase();
    CardRequest cardRequest = getCardRequest(CREDIT);
    cardRequest.setUserId(user.getId());

    mvc.perform(post(BASE_URL)
        .content(new ObjectMapper().writeValueAsString(cardRequest))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.type").value(CREDIT.name()))
        .andExpect(jsonPath("$.limitCredit").value(cardRequest.getLimit()))
        .andExpect(jsonPath("$.status").value(DISABLED.name()));
  }

  @Test
  @WithMockUser
  void givenFindAllCardsThnReturnCardArray () throws Exception {
    mvc.perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

}
