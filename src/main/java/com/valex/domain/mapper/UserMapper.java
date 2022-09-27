package com.valex.domain.mapper;

import com.valex.domain.dto.UserDto;
import com.valex.domain.model.User;
import com.valex.utils.Encoder;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public class UserMapper {

  public User dtoToUser (UserDto userDto) {
    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(Encoder.encode(userDto.getPassword()));
    user.setCpf(userDto.getCpf());

    return user;
  }
}
