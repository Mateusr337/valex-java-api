package com.valex.service;

import com.valex.domain.exception.ConflictException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.User;
import com.valex.domain.dto.UserDto;
import com.valex.repository.UserRepository;
import com.valex.utils.Encoder;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  public User findByIdOrFail (Long id) {
    Optional<User> foundUser = this.userRepository.findById(id);
    return foundUser.orElseThrow(() -> new NotFoundException("User not found"));
  }

  public void create (UserDto userDto) {
    User foundUser = this.userRepository.findByEmail(userDto.getEmail());

    if (foundUser != null) {
      throw new ConflictException ("This email already exist!");
    }

    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(Encoder.encode(userDto.getPassword()));
    user.setCpf(userDto.getCpf());

    this.userRepository.save(user);
  }

  public void delete (Long id) {
    User user = findByIdOrFail(id);
    this.userRepository.delete(user);
  }

}
