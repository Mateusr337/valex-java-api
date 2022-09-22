package com.valex.service;

import com.valex.domain.exception.UnauthorizedException;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserAuthService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserAuthService(UserRepository userRepository) {
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
