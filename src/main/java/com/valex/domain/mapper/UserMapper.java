package com.valex.domain.mapper;

import com.valex.domain.dto.UserDto;
import com.valex.domain.model.User;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public class UserMapper {

  public UserDto requestToDto (UserRequest userRequest) {
    UserDto userDto = new UserDto();
    userDto.setName(userRequest.getName());
    userDto.setEmail(userRequest.getEmail());
    userDto.setPassword(userRequest.getPassword());
    userDto.setCpf(userRequest.getCpf());

    return userDto;
  }

  public UserDto modelToDto (User user) {
    UserDto userDto = new UserDto();

    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());
    userDto.setCpf(user.getCpf());
    userDto.setPassword(user.getPassword());

    return userDto;
  }

  public UserResponse dtoToResponse (UserDto userDto) {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(userDto.getId());
    userResponse.setName(userDto.getName());
    userResponse.setEmail(userDto.getEmail());
    userResponse.setCpf(userDto.getCpf());

    return userResponse;
  }

  public User dtoToModel (UserDto userDto) {
    User user = new User();

    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setCpf(userDto.getCpf());
    user.setPassword(userDto.getPassword());

    return user;
  }

  public List<UserDto> modelToDto (List<User> userList) {
    List<UserDto> userDtoList = new ArrayList<>();

    for (User user : userList) {
      UserDto userDto = new UserDto();
      userDto.setId(user.getId());
      userDto.setName(user.getName());
      userDto.setEmail(user.getEmail());
      userDto.setCpf(user.getCpf());
      userDto.setPassword(user.getPassword());
      userDtoList.add(userDto);
    }

    return userDtoList;
  }

  public List<UserResponse> dtoToResponse (List<UserDto> userDtoList) {
    List<UserResponse> userResponseList = new ArrayList<>();

    for (UserDto userDto : userDtoList) {
      UserResponse userResponse = new UserResponse();
      userResponse.setId(userDto.getId());
      userResponse.setName(userDto.getName());
      userResponse.setEmail(userDto.getEmail());
      userResponse.setCpf(userDto.getCpf());
      userResponseList.add(userResponse);
    }
    ;
    return userResponseList;
  }

}
