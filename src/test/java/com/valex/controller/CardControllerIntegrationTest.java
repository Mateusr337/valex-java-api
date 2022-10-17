package com.valex.controller;

import static com.valex.domain.mother.UserMother.getUserRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.domain.request.UserRequest;
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

@Sql(value = "/clean-database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class CardControllerIntegrationTest {

  private static final String BASE_URL = "/users";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private CardRepository cardRepository;

  @Test
  @WithMockUser
  void givenCreateCardThenReturnCreatedCard () {

  }



}
