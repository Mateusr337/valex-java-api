package com.valex.domain.dto;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CardDto {

  private Long id;
  private Long userId;
  private String userName;
  private String number;
  private CardStatus status;
  private String cvv;
  private String passcode;
  private CardType type;
  private Long limitCredit;

}
