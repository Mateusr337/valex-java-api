package com.valex.domain.dto;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
  private Date expirationDate;

}
