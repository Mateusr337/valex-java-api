package com.valex.domain.dto;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
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
  private String cvv;
  private CardStatus status;
  private String passcode;
  private CardType type;
  private Long limit;

}
