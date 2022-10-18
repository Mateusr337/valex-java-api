package com.valex.domain.vo;

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
public class CreateOrderVo {

  private Card card;

  private CardType type;

  private String shopName;

  private List<Product> products;

  private String passcode;

}
