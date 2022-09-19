package com.valex.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class UserDto {

  @NotNull
  private String name;

  @NotNull
  @Email
  private String email;

  @NotNull
  @Size (min = 6, max = 8, message = "Password must have length between 6 and 8 digits")
  private String password;

  @NotNull
  @Size (min = 11, max = 11)
  private String cpf;
}
