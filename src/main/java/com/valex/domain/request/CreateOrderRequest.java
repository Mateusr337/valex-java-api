package com.valex.domain.request;

import com.valex.domain.model.Product;
import java.util.List;
import javax.validation.Valid;
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

  @NotNull (message = "{cardId.not.null}")
  private Long cardId;

  @NotBlank (message = "{card.type.null.empty}")
  private String type;

  @NotBlank (message = "{shopName.not.empty}")
  private String shopName;

  @NotNull (message = "{products.not.null}")
  @Size (min = 1, message = "{products.not.empty}")
  @Valid
  private List<ProductOrderRequest> products;

  @NotBlank (message = "{passcode.not.null}")
  private String passcode;

}