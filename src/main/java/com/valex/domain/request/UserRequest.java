package com.valex.domain.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

  @NotBlank(message = "{name.null.empty}")
  private String name;

  @Email (message = "{email.invalid}")
  @NotBlank (message = "{email.null.empty}")
  private String email;

  @NotBlank (message = "{password.null.empty}")
  @Size (min = 6, max = 8, message = "{password.length}")
  private String password;

  @NotBlank (message = "{cpf.null.empty}")
  @CPF (message = "{cpf.invalid}")
  private String cpf;

}
