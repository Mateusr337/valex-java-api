package com.valex.demon.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class UserDto {

  @NotNull
  private String name;

  @NotNull
  @Email
  private String email;
}
