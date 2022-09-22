package com.valex.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginDto {

  @NotBlank
  @Email
  private String email;

  @NotNull
  private String password;
}
