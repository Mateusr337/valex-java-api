package com.valex.domain.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ActivateCardRequest {

  @NotBlank (message = "{passcode.null.empty}")
  @Size (min = 4, max = 4, message = "{passcode.invalid}")
  private String passcode;

}
