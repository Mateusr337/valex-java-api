package com.valex.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;

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

  @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
  private Set<Card> cards;

}
