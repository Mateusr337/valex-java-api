package com.valex.controller;

import com.valex.domain.Dto.AuthDto;
import com.valex.domain.Dto.UserDto;
import com.valex.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/authentications")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  public void login (@RequestBody AuthDto authDto) {

    this.authService.login(authDto);

  }
}
