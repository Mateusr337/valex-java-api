package com.valex.domain.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CardDto {

  @NotBlank
  @NotNull
  private Long userId;

  @NotBlank
  @NotNull
  private String type;

  private Long limit;
}
