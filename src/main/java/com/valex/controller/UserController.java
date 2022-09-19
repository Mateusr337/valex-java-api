package com.valex.controller;

import com.valex.domain.dto.UserDto;
import com.valex.domain.model.User;
import com.valex.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/users")
public class UserController {

  private final UserService userService;

  public UserController (UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAll () {
    return this.userService.getAll();
  }

  @PostMapping
  @ResponseStatus (code = HttpStatus.CREATED)
  public void create (@RequestBody @Valid UserDto userDto) {
    this.userService.create(userDto);
  }

}
