package com.valex.Factory;

import static com.valex.domain.mother.UserMother.getUser;
import static com.valex.domain.mother.UserMother.getUserRequest;

import com.valex.controller.UserController;
import com.valex.domain.model.User;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import com.valex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  @Autowired
  private UserRepository repository;

  public User createUserInTheDatabase () {
    User user = getUser();
    return repository.save(user);
  }

  public User createUserInTheDatabase (User user) {
    return repository.save(user);
  }
}
