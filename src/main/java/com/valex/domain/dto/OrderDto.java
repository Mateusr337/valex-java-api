package com.valex.domain.dto;

import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import java.util.Date;
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
public class OrderDto {

  private Long id;

  private CardDto card;

  private CardType purchaseType;

  private String shopName;

  private List<ProductDto> products;

  private Date date;

}