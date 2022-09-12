package com.valex.demon.model;

import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity (name = "users")
public class User {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  @Email
  @Column (unique = true)
  private String email;

  @NotNull
  private String password;

}
