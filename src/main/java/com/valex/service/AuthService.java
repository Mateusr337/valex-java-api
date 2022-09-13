package com.valex.service;

import com.valex.domain.Dto.AuthDto;
import com.valex.domain.Dto.UserDto;
import com.valex.domain.exception.UnauthorizedException;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  private UserService userService;


  public void login (AuthDto authDto) {
    System.out.println(authDto.getEmail());

    User user = userService.getByEmailOrFail(authDto.getEmail());

    boolean passwordIsValid = encoder.matches(authDto.getPassword(), user.getPassword());

    if (!passwordIsValid) {
      throw new UnauthorizedException();
    }
  }

}
