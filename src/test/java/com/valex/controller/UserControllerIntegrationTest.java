package com.valex.controller;

import static com.valex.domain.mother.UserMother.getUser;
import static com.valex.domain.mother.UserMother.getUserRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.domain.model.User;
import com.valex.domain.mother.LoginMother;
import com.valex.domain.request.LoginRequest;
import com.valex.domain.request.UserRequest;
import com.valex.repository.UserRepository;
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

@Sql (value = "/clean-database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource ("classpath:application-test.properties")
@ActiveProfiles ("test")
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerIntegrationTest {

  private static final String BASE_URL = "/users";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepository userRepository;

  @Test
  void givenValidUserRequestThenReturnCreatedUser () throws Exception {
    UserRequest userRequest = getUserRequest();

    mvc.perform(post(BASE_URL)
        .content(new ObjectMapper().writeValueAsString(userRequest))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(status().isCreated());
  }

  @Test
  void givenValidUserLoginThenReturnToken () throws Exception {
    createUserInTheDatabase();
    LoginRequest loginRequest = LoginMother.getLoginRequest();

    mvc.perform(post("/login")
        .content(new ObjectMapper().writeValueAsString(loginRequest))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isString());
  }

  @Test
  @WithMockUser
  void givenFindAllUsersThenReturnUserList () throws Exception {
    mvc.perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$"). isArray());
  }

  @Test
  @WithMockUser
  void givenDeleteExistUserIdThnReturnStatusNoContext () throws Exception {
    createUserInTheDatabase();
    User user = userRepository.findAll().get(0);

    String URL = BASE_URL + "/" + user.getId();

    mvc.perform(delete(URL))
        .andExpect(status().isNoContent());
  }

  private void createUserInTheDatabase () throws Exception {
    UserRequest userRequest = getUserRequest();

    mvc.perform(post(BASE_URL)
        .content(new ObjectMapper().writeValueAsString(userRequest))
        .contentType(MediaType.APPLICATION_JSON));
  }
}

