package com.valex.domain.validation;

import com.valex.domain.exception.BadRequestException;

public final class CardTypeAndLimitValidation {

  public static void valid(String type, long limit) {

    if ( !type.equals("debit") && !type.equals("credit") ) {
      throw new BadRequestException("Card type not allowed! must be 'credit' or 'debit'");
    }

    if (type.equals("credit")) { validCreditCard (limit); }

    if (type.equals("debit")) { validDebitCard (limit); }
  }

  private static void validCreditCard (long limit) {
    long minCardLimit = 5000;

    if (limit <= minCardLimit) {
      throw new BadRequestException("Credit card must have limit greater than or equal 5000!");
    }
  }

  private static void validDebitCard (long limit) {
    if (limit != 0) {
      throw new BadRequestException("Debit card must have limit equal zero!");
    }
  }

}