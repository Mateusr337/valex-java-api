package com.valex.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Encoder {

  public static PasswordEncoder getEncoder () {
    return new BCryptPasswordEncoder();
  }
}
