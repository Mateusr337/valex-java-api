package com.valex.controller.integration;

import static com.valex.domain.mother.UserMother.getUserRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@Sql (value = "/clean-database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource ("classpath:application-test.properties")
public class UserControllerIntTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void whenCreatedUserThenReturnThisUser () {

    UserRequest newUser = getUserRequest();
    HttpEntity<UserRequest> HttpEntity = new HttpEntity<>(newUser);

    ResponseEntity<UserResponse> response = testRestTemplate.exchange(
        "/users",
        HttpMethod.POST,
        HttpEntity,
        UserResponse.class
    );

    UserResponse bodyRes = response.getBody();

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(UserResponse.class, bodyRes.getClass());
    assertNotNull(bodyRes.getId());
    assertEquals(newUser.getName(), bodyRes.getName());
    assertEquals(newUser.getEmail(), bodyRes.getEmail());
    assertEquals(newUser.getCpf(), bodyRes.getCpf());
  }

}

