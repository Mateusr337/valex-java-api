package com.valex.domain.jwt.service;

import com.valex.domain.exception.NotFoundException;
import com.valex.domain.jwt.data.UserDataDetails;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws NotFoundException {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new NotFoundException("User [" + email + "] not found!");
    }

    return new UserDataDetails( user );
  }
}
