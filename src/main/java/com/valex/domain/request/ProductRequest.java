package com.valex.domain.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  @NotBlank (message = "{order.product.title.not.empty}")
  private String title;

  @NotBlank (message = "{order.product.description.not.empty}")
  private String description;

  @NotNull (message = "{order.product.price.not.null}")
  private Long price;

}
