package com.valex.domain.mother;

import static com.valex.domain.mother.UserMother.getUserRequest;

import com.valex.domain.request.LoginRequest;
import com.valex.domain.request.UserRequest;

public final class LoginMother {

  public static LoginRequest getLoginRequest () {
    UserRequest userRequest = getUserRequest();

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(userRequest.getEmail());
    loginRequest.setPassword(userRequest.getPassword());

    return loginRequest;
  }
}
