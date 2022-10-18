package com.valex.domain.mapper;

import static com.valex.domain.mother.UserMother.getUser;
import static com.valex.domain.mother.UserMother.getUserDto;
import static com.valex.domain.mother.UserMother.getUserList;
import static com.valex.domain.mother.UserMother.getUserRequest;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import com.valex.domain.dto.UserDto;
import com.valex.domain.mapper.impl.UserMapper;
import com.valex.domain.model.User;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperUnitTest {

  @InjectMocks
  private UserMapper userMapper;

  @Test
  void whenRequestToReturnDto () {
    UserRequest userRequest = getUserRequest();

    UserDto response = userMapper.requestToDto(userRequest);

    then(response.getClass()).isEqualTo(UserDto.class);
    then(response.getName()).isEqualTo(userRequest.getName());
    then(response.getEmail()).isEqualTo(userRequest.getEmail());
    then(response.getCpf()).isEqualTo(userRequest.getCpf());
    then(response.getPassword()).isEqualTo(userRequest.getPassword());
    then(response.getId()).isNull();

  }

  @Test
  void WhenModelToReturnDto () {
    User user = getUser();

    UserDto response = userMapper.modelToDto(user);

    then(response.getClass()).isEqualTo(UserDto.class);
    then(response.getName()).isEqualTo(user.getName());
    then(response.getEmail()).isEqualTo(user.getEmail());
    then(response.getCpf()).isEqualTo(user.getCpf());
    then(response.getPassword()).isEqualTo(user.getPassword());
    then(response.getId()).isEqualTo(user.getId());
  }

  @Test
  void whenDtoToReturnResponse () {
    UserDto userDto = getUserDto();

    UserResponse response = userMapper.dtoToResponse(userDto);

    then(response.getClass()).isEqualTo(UserResponse.class);
    then(response.getName()).isEqualTo(userDto.getName());
    then(response.getEmail()).isEqualTo(userDto.getEmail());
    then(response.getCpf()).isEqualTo(userDto.getCpf());
    then(response.getId()).isEqualTo(userDto.getId());
  }

  @Test
  void whenDtoToReturnModel () {
    UserDto userDto = getUserDto();

    User response = userMapper.dtoToModel(userDto);

    then(response.getClass()).isEqualTo(User.class);
    then(response.getName()).isEqualTo(userDto.getName());
    then(response.getEmail()).isEqualTo(userDto.getEmail());
    then(response.getCpf()).isEqualTo(userDto.getCpf());
    then(response.getId()).isEqualTo(userDto.getId());
    then(response.getPassword()).isEqualTo(userDto.getPassword());
  }

  @Test
  void whenModelListToReturnDtoList () {
    List<User> userList = getUserList();

    List<UserDto> response = userMapper.modelToDto(userList);

    assertEquals(userList.size(), response.size());
    UserDto firstUser = response.get(0);

    then(firstUser.getClass()).isEqualTo(UserDto.class);
    then(firstUser.getName()).isEqualTo(userList.get(0).getName());
    then(firstUser.getEmail()).isEqualTo(userList.get(0).getEmail());
    then(firstUser.getCpf()).isEqualTo(userList.get(0).getCpf());
    then(firstUser.getId()).isEqualTo(userList.get(0).getId());
    then(firstUser.getPassword()).isEqualTo(userList.get(0).getPassword());
  }

  @Test
  void whenDtoListToReturnResponseList () {
    UserDto userDto = getUserDto();

    List<UserResponse> response = userMapper.dtoToResponse(List.of(userDto));

    assertEquals(1, response.size());
    UserResponse firstUser = response.get(0);

    then(firstUser.getClass()).isEqualTo(UserResponse.class);
    then(firstUser.getName()).isEqualTo(userDto.getName());
    then(firstUser.getEmail()).isEqualTo(userDto.getEmail());
    then(firstUser.getCpf()).isEqualTo(userDto.getCpf());
    then(firstUser.getId()).isEqualTo(userDto.getId());
  }
}