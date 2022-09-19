package com.valex.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name = "cards")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn (name = "card", nullable = false)
  private User user = new User ();

  @Column (nullable = false)
  private String userName;

  @Column (unique = true, nullable = false)
  private String number;

  @Column (nullable = false)
  private String status;

  @Column (nullable = false)
  private String cvv;

  private String password;

  @Column (nullable = false)
  private String type;
}
