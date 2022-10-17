package com.valex.Factory;

import static com.valex.domain.mother.UserMother.getUserRequest;

import com.valex.controller.UserController;
import com.valex.domain.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  @Autowired
  private UserController userController;

  public void createUserInTheDatabase () {
    UserRequest userRequest = getUserRequest();
    userController.create(userRequest);
  }
}
