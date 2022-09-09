package com.valex.demon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity (name = "users")
public class User {

  @Id
  @Column
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String email;

  public Long getId () {
    return this.id;
  }

  public String getName () {
    return this.name;
  }

  public void setName (String name) {
    this.name = name;
  }

  public String getEmail () {
    return this.email;
  }

  public void setEmail (String email) {
    this.email = email;
  }
}
