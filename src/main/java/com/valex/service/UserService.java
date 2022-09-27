package com.valex.service;

import com.valex.domain.dto.UserDto;
import com.valex.domain.exception.ConflictException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.mapper.UserMapper;
import com.valex.domain.model.User;
import com.valex.domain.request.UserRequest;
import com.valex.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  public List<UserDto> findAll() {
    List<User> users = this.userRepository.findAll();

    return userMapper.modelToDto(users);
  }

  public User findByIdOrFail (Long id) {
    Optional<User> foundUser = this.userRepository.findById(id);
    return foundUser.orElseThrow(() -> new NotFoundException("User not found"));
  }

  public UserDto create (UserRequest userRequest) {
    User foundUser = this.userRepository.findByEmail(userRequest.getEmail());

    if (foundUser != null) {
      throw new ConflictException ("This email already exist");
    }

    User user = this.userRepository.save(userMapper.requestToModel(userRequest));
    return userMapper.modelToDto(user);
  }

  public void delete (Long id) {
    User user = findByIdOrFail(id);
    this.userRepository.deleteById(id);
  }

}
