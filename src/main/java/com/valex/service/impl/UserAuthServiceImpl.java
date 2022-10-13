package com.valex.service.impl;

import com.valex.domain.exception.UnauthorizedException;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserAuthServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserAuthServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User loadUserByUsername(String email) throws UnauthorizedException {
    User user = this.userRepository.findByEmail(email);

    if (user == null) {
      throw new UnauthorizedException("User unauthorized!");
    }

    return user;
  }
}
