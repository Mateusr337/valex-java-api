package com.valex.domain.mapper;

import static com.valex.domain.mother.UserMother.getUser;
import static com.valex.domain.mother.UserMother.getUserDto;
import static com.valex.domain.mother.UserMother.getUserList;
import static com.valex.domain.mother.UserMother.getUserRequest;
import static org.junit.jupiter.api.Assertions.*;

import com.valex.domain.dto.UserDto;
import com.valex.domain.model.User;
import com.valex.domain.mother.UserMother;
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

    assertEquals(UserDto.class, response.getClass());
    assertEquals(userRequest.getName(), response.getName());
    assertEquals(userRequest.getEmail(), response.getEmail());
    assertEquals(userRequest.getCpf(), response.getCpf());
    assertEquals(userRequest.getPassword(), response.getPassword());
    assertNull(response.getId());
  }

  @Test
  void WhenModelToReturnDto () {
    User user = getUser();

    UserDto response = userMapper.modelToDto(user);

    assertEquals(UserDto.class, response.getClass());
    assertEquals(user.getName(), response.getName());
    assertEquals(user.getEmail(), response.getEmail());
    assertEquals(user.getCpf(), response.getCpf());
    assertEquals(user.getPassword(), response.getPassword());
    assertEquals(user.getId(), response.getId());
  }

  @Test
  void whenDtoToReturnResponse () {
    UserDto userDto = getUserDto();

    UserResponse response = userMapper.dtoToResponse(userDto);

    assertEquals(UserResponse.class, response.getClass());
    assertEquals(userDto.getName(), response.getName());
    assertEquals(userDto.getEmail(), response.getEmail());
    assertEquals(userDto.getCpf(), response.getCpf());
    assertEquals(userDto.getId(), response.getId());
  }

  @Test
  void whenDtoToReturnModel () {
    UserDto userDto = getUserDto();

    User response = userMapper.dtoToModel(userDto);

    assertEquals(User.class, response.getClass());
    assertEquals(userDto.getName(), response.getName());
    assertEquals(userDto.getEmail(), response.getEmail());
    assertEquals(userDto.getCpf(), response.getCpf());
    assertEquals(userDto.getPassword(), response.getPassword());
    assertEquals(userDto.getId(), response.getId());
  }

  @Test
  void whenModelListToReturnDtoList () {
    List<User> userList = getUserList();

    List<UserDto> response = userMapper.modelToDto(userList);

    assertEquals(userList.size(), response.size());
    UserDto firstUser = response.get(0);

    assertEquals(UserDto.class, firstUser.getClass());
    assertEquals(userList.get(0).getName(), firstUser.getName());
    assertEquals(userList.get(0).getEmail(), firstUser.getEmail());
    assertEquals(userList.get(0).getCpf(), firstUser.getCpf());
    assertEquals(userList.get(0).getPassword(), firstUser.getPassword());
    assertEquals(userList.get(0).getId(), firstUser.getId());
  }

  @Test
  void whenDtoListToReturnResponseList () {
    UserDto userDto = getUserDto();

    List<UserResponse> response = userMapper.dtoToResponse(List.of(userDto));

    assertEquals(1, response.size());
    UserResponse firstUser = response.get(0);

    assertEquals(UserResponse.class, firstUser.getClass());
    assertEquals(userDto.getName(), firstUser.getName());
    assertEquals(userDto.getEmail(), firstUser.getEmail());
    assertEquals(userDto.getCpf(), firstUser.getCpf());
    assertEquals(userDto.getId(), firstUser.getId());
  }
}