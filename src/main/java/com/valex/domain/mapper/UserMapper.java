package com.valex.domain.mapper;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.UserDto;
import com.valex.domain.model.User;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import com.valex.utils.Encoder;
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

  public User requestToModel (UserRequest userRequest) {
    UserDto userDto = this.requestToDto(userRequest);

    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setCpf(userDto.getCpf());
    user.setPassword(Encoder.encode(userDto.getPassword()));

    return user;
  }

  public UserDto modelToDto (User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());
    userDto.setCpf(user.getCpf());
    userDto.setPassword(Encoder.encode(user.getPassword()));

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

  public List<UserDto> modelToDto (List<User> userList) {
    List<UserDto> userDtoList = new ArrayList<>();

    for (int i = 0; i < userList.size(); i++) {
      User user = userList.get(i);

      UserDto userDto = new UserDto();
      userDto.setId(user.getId());
      userDto.setName(user.getName());
      userDto.setEmail(user.getEmail());
      userDto.setCpf(user.getCpf());
      userDto.setPassword(user.getPassword());
      userDtoList.set(i, userDto);
    };
    return userDtoList;
  }

  public List<UserResponse> dtoToResponse (List<UserDto> userDtoList) {
    UserResponse[] userResponseList = new UserResponse[ userDtoList.size() ];

    for (int i = 0; i < userDtoList.size(); i++) {
      UserDto userDto = userDtoList.get(i);

      UserResponse userResponse = new UserResponse();
      userResponse.setId(userDto.getId());
      userResponse.setName(userDto.getName());
      userResponse.setEmail(userDto.getEmail());
      userResponse.setCpf(userDto.getCpf());
      userResponseList[i] = userResponse;
    };
    return Arrays.stream(userResponseList).toList();
  }

}
