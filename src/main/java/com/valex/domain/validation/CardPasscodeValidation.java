package com.valex.domain.validation;

import com.valex.domain.exception.BadRequestException;

public final class CardPasscodeValidation {

  public static void valid (String passcodeStr) {

    for (int i = 0; i < passcodeStr.length(); i++) {
      validateCharIsDigit(passcodeStr.charAt(i));
    }
  }

  private static void validateCharIsDigit (Character character) {
    if (!Character.isDigit(character)) {
      throw new BadRequestException("Passcode must be a string with 4 numbers!");
    }
  }

}
