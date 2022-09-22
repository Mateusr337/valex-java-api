package com.valex.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table (name = "users")
public class User {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  @Column (nullable = false)
  private String name;

  @Column (unique = true, nullable = false)
  private String email;

  @Column (nullable = false)
  private String password;

  @Column (nullable = false)
  private String cpf;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL
  )
  @JsonIgnore
  private Set<Card> cards;

}
