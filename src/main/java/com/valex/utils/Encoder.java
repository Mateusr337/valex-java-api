package com.valex.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Encoder {
  public Encoder () {}

  public static String encode (String password) {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(password);

  }

  public static PasswordEncoder getEncoder () {
    return new BCryptPasswordEncoder();
  }
}
