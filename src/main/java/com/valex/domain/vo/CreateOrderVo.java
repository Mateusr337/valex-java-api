package com.valex.domain.vo;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.request.ProductRequest;
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
public class CreateOrderVo {

  private CardDto card;

  private CardType purchaseType;

  private String shopName;

  private List<ProductRequest> products;

  private String passcode;

  private Date date;

}
