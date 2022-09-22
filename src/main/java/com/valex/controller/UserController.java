package com.valex.controller;

import com.valex.domain.dto.UserDto;
import com.valex.domain.model.User;
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


  @GetMapping
  public List<User> getAll () {
    return this.userService.findAll();
  }

  @PostMapping
  @ResponseStatus (HttpStatus.CREATED)
  public void create (@RequestBody @Valid UserDto userDto) {
    this.userService.create(userDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus (HttpStatus.NO_CONTENT)
  public void delete (@PathVariable ("id") Long userId) {
    this.userService.delete(userId);
  }

}
