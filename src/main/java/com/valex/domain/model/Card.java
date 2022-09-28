package com.valex.domain.model;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
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
  @JoinColumn (name = "user_id")
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

  private String passcode;

  @Column (nullable = false)
  @Enumerated (EnumType.STRING)
  private CardType type;

  @Column (nullable = false)
  private Long limitCredit;
}
