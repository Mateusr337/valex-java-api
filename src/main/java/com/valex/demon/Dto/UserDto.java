package com.valex.demon.Dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserDto {

  @NotNull
  private String name;

  @NotNull
  @Email
  private String email;

  @NotNull
  @Length (min = 6, max = 8)
  private String password;
}
