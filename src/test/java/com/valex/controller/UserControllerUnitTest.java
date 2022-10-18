package com.valex.controller;

import static com.valex.domain.mother.UserMother.*;
import static com.valex.domain.mother.UserMother.getUserResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.domain.dto.UserDto;
import com.valex.domain.mapper.impl.UserMapper;
import com.valex.domain.request.UserRequest;
import com.valex.domain.response.UserResponse;
import com.valex.service.impl.UserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUnitTest {

  private final static String BASE_URL = "/users";

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserMapper userMapper;

  @MockBean
  private UserServiceImpl userService;

  @InjectMocks
  private UserController userController;

  @Test
  void givenCratedNewUserThenReturnThisUser () throws Exception {
    UserResponse userResponse = getUserResponse();

    given(userMapper.requestToDto(any(UserRequest.class))).willReturn(getUserDtoWithoutId());
    given(userService.create(any(UserDto.class))).willReturn(getUserDto());
    given(userMapper.dtoToResponse(any(UserDto.class))).willReturn(userResponse);

    mvc.perform(post(BASE_URL)
        .content(new ObjectMapper().writeValueAsString(getUserRequest()))
        .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(userResponse.getName()))
        .andExpect(jsonPath("$.id").value(userResponse.getId()))
        .andExpect(jsonPath("$.cpf").value(userResponse.getCpf()))
        .andExpect(jsonPath("$.password").doesNotHaveJsonPath());


    verify(userMapper).requestToDto(any(UserRequest.class));
    verify(userMapper).dtoToResponse(any(UserDto.class));
  }

  @Test
  @WithMockUser
  void givenFindAllUsersThenReturnArrayOfUsers () throws Exception {
    UserResponse userResponse = getUserResponse();

    given(userService.findAll()).willReturn(List.of(getUserDto()));
    given(userMapper.dtoToResponse(anyList())).willReturn(List.of(userResponse));

    mvc.perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(userResponse.getId()))
        .andExpect(jsonPath("$[0].name").value(userResponse.getName()))
        .andExpect(jsonPath("$[0].email").value(userResponse.getEmail()))
        .andExpect(jsonPath("$[0].cpf").value(userResponse.getCpf()))
        .andExpect(jsonPath("$[0].password").doesNotHaveJsonPath())
        .andExpect(jsonPath("$[1]").doesNotHaveJsonPath());


    verify(userService).findAll();
    verify(userMapper).dtoToResponse(anyList());
  }

  @Test
  @WithMockUser
  void givenDeleteUserRequestThenReturnStatusNoContent () throws Exception {
    UserDto userDto = getUserDto();

    String URL = BASE_URL + "/" + userDto.getId();
    mvc.perform(delete(URL))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotHaveJsonPath());

    verify(userService).delete(userDto.getId());
  }

}
