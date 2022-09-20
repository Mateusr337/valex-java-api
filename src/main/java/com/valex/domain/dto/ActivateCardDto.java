package com.valex.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ActivateCardDto {

  @NotNull @NotEmpty
  @Size (min = 4, max = 4)
  private String password;

}
