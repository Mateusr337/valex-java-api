package com.valex.utils;

import com.valex.domain.exception.UnauthorizedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Encoder {
  public static String encode (String password) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(password);
  }

  public static void matches (String string, String encoded) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    boolean match = passwordEncoder.matches(string, encoded);

    if (!match) {
      throw new UnauthorizedException("wrong password");
    }
  }

  public static PasswordEncoder getNewEncoder() {
    return new BCryptPasswordEncoder();
  }
}
