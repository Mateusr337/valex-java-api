package com.valex.domain.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity (name = "users")
public class User {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column (unique = true)
  private String email;

  private String password;

  private String cpf;
}
