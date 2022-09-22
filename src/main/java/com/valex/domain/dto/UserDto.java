package com.valex.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class UserDto {

  @NotBlank(message = "name not be null or empty!")
  private String name;

  @Email (message = "this email is not valid")
  @NotBlank (message = "email not be null or empty!")
  private String email;

  @NotBlank (message = "password not be null or empty!")
  @Size (min = 6, max = 8, message = "Password must have length between 6 and 8 digits")
  private String password;

  @NotBlank (message = "CPF not be null or empty!")
  @Size (min = 11, max = 11)
  @CPF
  private String cpf;

}
