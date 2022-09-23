package com.valex.domain.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CardDto {

  @NotNull (message = "{user.id.null}")
  private Long userId;

  @NotBlank (message = "{card.type.null.empty}")
  private String type;

  @NotNull (message = "{limit.null}")
  private Long limit;
}
