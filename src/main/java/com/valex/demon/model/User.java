package com.valex.demon.model;

import javax.persistence.*;
import lombok.*;

@Entity (name = "users")
@Getter
@Setter
public class User {

  @Id
  @Column
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

//  @Column
  private String name;

//  @Column
  private String email;

}
