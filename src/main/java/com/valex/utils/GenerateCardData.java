package com.valex.utils;

import java.util.Random;

public final class GenerateCardData {

  private static final Random random = new Random();

  public static String Number () {
    StringBuilder cardNumber = new StringBuilder();

    for (int i = 0; i < 16; i++) {
      int randomNumber = random.nextInt(9);
      cardNumber.append(Integer.toString(randomNumber));
    }
    return cardNumber.toString();
  }

  public static String CVV () {
    StringBuilder CVV = new StringBuilder();

    for (int i = 0; i < 3; i++) {
      int randomNumber = random.nextInt(9);
      CVV.append(Integer.toString(randomNumber));
    }
    return CVV.toString();
  }

}
