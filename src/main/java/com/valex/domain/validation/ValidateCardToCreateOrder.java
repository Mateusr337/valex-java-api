package com.valex.domain.validation;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;

import com.valex.domain.dto.CardDto;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.request.ProductOrderRequest;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.utils.Encoder;
import java.util.Date;
import java.util.List;

public final class ValidateCardToCreateOrder {

  public static void valid (CardDto databaseCard, CreateOrderVo requestData) {
      if (databaseCard.getStatus() != ACTIVE) {
        throw new BadRequestException("Card not been activated.");
      }

      if (databaseCard.getExpirationDate().getTime() < new Date().getTime()) {
        System.out.println("///////");
        System.out.println(databaseCard.getExpirationDate().getTime() < new Date().getTime());
        throw new BadRequestException("Card is expired.");
      }

      if (requestData.getPasscode() != null) validateInPersonShop(databaseCard, requestData);
      //call virtual purchase validations

      Long totalPrice = calculateTotalPrice(requestData.getProducts());
      if (requestData.getType() == DEBIT) validateAmount(databaseCard.getBalance(), totalPrice);
      if (requestData.getType() == CREDIT) validateAmount(databaseCard.getLimit(), totalPrice);
  }

  private static Long calculateTotalPrice (List<ProductOrderRequest> products) {
    Long total = 0L;
    for (ProductOrderRequest product : products) {
      total += product.getPrice();
    }
    return total;
  }

  private static void validateAmount (Long amountCard, Long amountPurchase ) {
    if (amountPurchase > amountCard) throw new BadRequestException("Amount unauthorized.");
  }

  private static void validateInPersonShop(CardDto databaseCard, CreateOrderVo requestData) {
    if (databaseCard.getType() != requestData.getType()) {
      throw new BadRequestException("Transaction type invalid.");
    }
    Encoder.matches(requestData.getPasscode(), databaseCard.getPasscode());
  }

  private static void validateVirtualShop (CardDto databaseCard, CreateOrderVo requestData) {
    // validar o cvv

    if (databaseCard.getType() != CREDIT || requestData.getType() != CREDIT) {
      throw new BadRequestException("Transaction type invalid.");
    }
  }
}
