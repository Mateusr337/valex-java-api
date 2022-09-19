package com.valex.service;

import com.valex.domain.exception.ConflictException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.User;
import com.valex.domain.dto.UserDto;
import com.valex.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  public List<User> getAll() {
    return this.userRepository.findAll();
  }

  public User getByEmail (String email) {
    return this.userRepository.findByEmail(email);
  }

  public User getByEmailOrFail (String email) {
    User user = this.userRepository.findByEmail(email);

    if (user == null) {
      throw new NotFoundException("User Not Found!");
    }
    return user;
  }

  public User findByIdOrFail (Long id) {
    @NotNull Optional<User> foundUser = this.userRepository.findById(id);

    if (foundUser.isEmpty()) {
      throw new NotFoundException ("Not found user!");
    }

    return foundUser.get();
  }

  public void create (@NotNull UserDto userDto) {
    User foundUser = this.userRepository.findByEmail(userDto.getEmail());

    if (foundUser != null) {
      throw new ConflictException ("This email already exist!");
    }

    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(encoder.encode(userDto.getPassword()));
    user.setCpf(userDto.getCpf());

    this.userRepository.save(user);
  }

}
