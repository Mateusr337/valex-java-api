package com.valex.domain.response;

import com.valex.domain.enumeration.CardType;
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
public class OrderResponse {

  private Long id;

  private Long cardId;

  private CardType purchaseType;

  private String shopName;

  private List<ProductResponse> products;

  private Date date;

}
