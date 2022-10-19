package com.valex.domain.vo;

import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.Product;
import com.valex.domain.request.ProductOrderRequest;
import java.util.List;
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

  private List<ProductOrderRequest> products;

  private String passcode;

}
