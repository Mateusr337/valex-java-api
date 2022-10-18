package com.valex.service.impl;

import static com.valex.utils.Encoder.encode;

import com.valex.domain.dto.UserDto;
import com.valex.domain.exception.ConflictException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.mapper.impl.UserMapper;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import com.valex.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  public List<UserDto> findAll() {
    List<User> users = this.userRepository.findAll();
    return this.userMapper.modelToDto(users);
  }

  public User findByIdOrFail (Long id) {
    Optional<User> foundUser = this.userRepository.findById(id);

    if (foundUser.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    return foundUser.get();
  }

  public UserDto create (UserDto userDto) {
    User foundUser = this.userRepository.findByEmail(userDto.getEmail());

    if (foundUser != null) {
      throw new ConflictException ("This email already exist");
    }
    userDto.setPassword(encode(userDto.getPassword()));
    User newUser = this.userRepository.save(userMapper.dtoToModel(userDto));
    return this.userMapper.modelToDto(newUser);
  }

  public void delete (Long id) {
    findByIdOrFail(id);
    this.userRepository.deleteById(id);
  }

}
