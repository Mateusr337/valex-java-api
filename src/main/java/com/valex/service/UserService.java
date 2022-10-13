package com.valex.service;


import com.valex.domain.dto.UserDto;
import com.valex.domain.model.User;
import java.util.List;

public interface UserService {

  List<UserDto> findAll();
  User findByIdOrFail (Long id);
  UserDto create (UserDto userDto);
  void delete (Long id);
}
