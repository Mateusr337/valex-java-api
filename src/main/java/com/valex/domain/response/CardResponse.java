package com.valex.domain.response;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CardResponse {

  private Long id;
  private Long userId;
  private String userName;
  private String number;
  private CardStatus status;
  private CardType type;
  private Long limitCredit;
  private Date expirationDate;

}
