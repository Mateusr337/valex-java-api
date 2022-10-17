package com.valex.controller;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getCardRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.Factory.CardFactory;
import com.valex.Factory.UserFactory;
import com.valex.domain.request.ActivateCardRequest;
import com.valex.domain.request.CardRequest;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.CardResponse;
import com.valex.domain.response.UserResponse;
import com.valex.repository.CardRepository;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
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

  @Autowired
  private CardRepository cardRepository;

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

  @Test
  @WithMockUser
  void givenActivatedCardReturnActivatedCard () throws Exception {
    UserResponse user = userFactory.createUserInTheDatabase();
    CardResponse card = cardFactory.createCardInTheDatabase(DEBIT, user.getId());

    String URL = BASE_URL + "/activate/" + card.getId();
    ActivateCardRequest activateRequest = new ActivateCardRequest("1234");

    mvc.perform(patch(URL)
            .content(new ObjectMapper().writeValueAsString(activateRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(card.getId()))
        .andExpect(jsonPath("$.status").value(ACTIVE.name()))
        .andExpect(jsonPath("$.passcode").doesNotExist());
  }

  @Test
  @WithMockUser
  void givenFindCardsByUserIdThenReturnCardListOfUser () throws Exception {
    UserResponse user1 = userFactory.createUserInTheDatabase();
    UserResponse user2 = userFactory.createUserInTheDatabase(getAlternativeUserRequest());

    CardResponse card1 = cardFactory.createCardInTheDatabase(DEBIT, user1.getId());
    cardFactory.createCardInTheDatabase(DEBIT, user2.getId());

    String URL = BASE_URL + "/users/" + user1.getId();

    mvc.perform(get(URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].userId").value(user1.getId()))
        .andExpect(jsonPath("$[0].id").value(card1.getId()))
        .andExpect(jsonPath("$[1]").doesNotExist());
  }

  private UserRequest getAlternativeUserRequest () {
    UserRequest user = new UserRequest();
    user.setName("Beltrano");
    user.setEmail("beltrano@email.com");
    user.setCpf("23454911087");
    user.setPassword("123456");

    return user;
  }

}
