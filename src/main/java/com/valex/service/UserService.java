package com.valex.service;

import com.valex.model.User;
import com.valex.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getAll() {

    return this.userRepository.findAll();
  }

  public void create(User user) {
    this.userRepository.save(user);
  }
}
