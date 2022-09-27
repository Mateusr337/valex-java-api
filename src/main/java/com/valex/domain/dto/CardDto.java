package com.valex.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardDto {

  @NotNull (message = "{user.id.null}")
  private Long userId;

  @NotBlank (message = "{card.type.null.empty}")
  private String type;

  @NotNull (message = "{limit.null}")
  private Long limit;
}
