package com.valex.domain.model;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity (name = "cards")
public class Card {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User user;

  @Column (nullable = false)
  private String userName;

  @Column (unique = true, nullable = false)
  private String number;

  @Column (nullable = false)
  @Enumerated (EnumType.STRING)
  private CardStatus status;

  @Column (nullable = false)
  private String cvv;

  @Column
  private String passcode;

  @Column (nullable = false)
  @Enumerated (EnumType.STRING)
  private CardType type;

  @Column (nullable = false)
  private Long limitCredit;

  @Column
  private Date expirationDate;

  @Column
  private Long balance;
}
