package com.valex.domain.request;

import com.valex.domain.model.Product;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

  @NotNull (message = "[ cardId ] cannot be null.")
  private Long cardId;

  @NotBlank (message = "{card.type.null.empty}")
  private String type;

  @NotBlank (message = "[ shopName ] cannot be null.")
  private String shopName;

  @NotNull (message = "[ products ] cannot be null.")
  @Size (min = 1, message = "In [ products[] ] must have one or more products.")
  private List<Product> products;

  @NotBlank (message = "[ passcode ] cannot must be null.")
  private String passcode;

}