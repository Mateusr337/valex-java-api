package com.valex.domain.request;

import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
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
public class OrderRequest {

  @NotNull (message = "Card id cannot be null.")
  private Long cardId;

  @NotBlank (message = "{card.type.null.empty}")
  private String type;

  @NotBlank (message = "shopName cannot be null.")
  private String shopName;

//  @Size (min = 1, message = "It must have one or more product.")
  private List<Product> products;

}