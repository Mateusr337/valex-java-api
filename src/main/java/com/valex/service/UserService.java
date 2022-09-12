package com.valex.service;

import com.valex.demon.model.User;
import com.valex.demon.Dto.UserDto;
import com.valex.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder encoder;

  public UserService (PasswordEncoder encoder) {

    this.encoder = encoder;
  }

  public List<User> getAll() {

    return this.userRepository.findAll();
  }

  public void create (UserDto userDto) {

    User user = new User();

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(encoder.encode(userDto.getPassword()));

    this.userRepository.save(user);
  }
}
