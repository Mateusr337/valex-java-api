package com.valex.controller;

import com.valex.domain.dto.UserDto;
import com.valex.domain.mapper.UserMapper;
import com.valex.domain.request.UserRequest;
import com.valex.domain.model.User;
import com.valex.domain.response.UserResponse;
import com.valex.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @GetMapping
  public List<UserResponse> findAll() {
    return userMapper.dtoToResponse(this.userService.findAll());
  }

  @PostMapping
  @ResponseStatus (HttpStatus.CREATED)
  public UserResponse create (@RequestBody @Valid UserRequest userRequest) {
    return userMapper.dtoToResponse(this.userService.create(userRequest));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus (HttpStatus.NO_CONTENT)
  public void delete (@PathVariable ("id") Long userId) {
    this.userService.delete(userId);
  }

}
