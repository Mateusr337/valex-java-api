package com.valex.domain.validation;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;

import com.valex.domain.dto.CardDto;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.model.Product;
import com.valex.domain.vo.CreateOrderVo;
import java.util.Date;
import java.util.List;

public final class ValidateCardToCreateOrder {

  public static void valid (CardDto databaseCard, CreateOrderVo requestData) {
      if (databaseCard.getStatus() != ACTIVE) {
        throw new BadRequestException("Card not been activated.");
      }

      if (databaseCard.getExpirationDate().getTime() < new Date().getTime()) {
        throw new BadRequestException("Card is expired.");
      }

      Long totalPrice = calculateTotalPrice(requestData.getProducts());
      if (totalPrice > databaseCard.getLimit()) {
        throw new BadRequestException("Card doesn't have enough balance.");
      }
  }

  private static Long calculateTotalPrice (List<Product> products) {
    Long total = 0L;

    for (Product product : products) {
      total += product.getPrice();
    }

    return total;
  }
}
