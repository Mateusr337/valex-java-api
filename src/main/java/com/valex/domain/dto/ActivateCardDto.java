package com.valex.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ActivateCardDto {

  @NotBlank (message = "{passcode.null.empty}")
  @Size (min = 4, max = 4, message = "{passcode.invalid}")
  private String passcode;

}
