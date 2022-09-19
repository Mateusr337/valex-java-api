package com.valex.domain.validation;

import com.valex.domain.exception.BadRequestException;
import java.util.ArrayList;
import java.util.Objects;

public final class CardTypeValidation {

  public static void valid(String type) {
    if (type == "debit")
      return;

    if (type == "credit")
      return;

    throw new BadRequestException("Credit type not allowed! must be 'credit' or 'debit'");
  }
}