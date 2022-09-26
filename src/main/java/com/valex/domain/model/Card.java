package com.valex.domain.model;

import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
