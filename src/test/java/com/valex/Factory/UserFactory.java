package com.valex.Factory;

import static com.valex.domain.mother.UserMother.getUserRequest;

import com.valex.controller.UserController;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  @Autowired
  private UserController userController;

  public UserResponse createUserInTheDatabase () {
    UserRequest userRequest = getUserRequest();
    return userController.create(userRequest);
  }
}
