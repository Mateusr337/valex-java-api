package com.valex.domain.validation;

import com.valex.domain.exception.BadRequestException;

public final class CardTypeAndLimitValidation {

  public static void valid(String type, long limit) {

    if ( !type.equals("DEBIT") && !type.equals("CREDIT") ) {
      throw new BadRequestException("Card type not allowed! must be 'CREDIT' or 'DEBIT'");
    }

    if (type.equals("CREDIT")) { validCreditCard (limit); }

    if (type.equals("DEBIT")) { validDebitCard (limit); }
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