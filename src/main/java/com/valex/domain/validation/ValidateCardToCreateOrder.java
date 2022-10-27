package com.valex.domain.validation;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;

import com.valex.domain.dto.CardDto;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.request.ProductRequest;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.utils.Encoder;
import java.util.Date;
import lombok.AllArgsConstructor;

public final class ValidateCardToCreateOrder {

  public static void valid (CardDto card, CreateOrderVo request
  ) {
      validateCardValidity(card);

      if (request
          .getPasscode() != null) validateInPersonShop(card, request);
      //call virtual purchase validations

      Long totalPrice = calculateTotalPrice(request);
      if (request
          .getPurchaseType() == DEBIT) validateAmount(card.getBalance(), totalPrice);
      if (request
          .getPurchaseType() == CREDIT) validateAmount(card.getLimit(), totalPrice);
  }

  private static void validateCardValidity (CardDto card) {
    if (card.getStatus() != ACTIVE) {
      throw new BadRequestException("Card not been activated.");
    }

    if (card.getExpirationDate().getTime() <= new Date().getTime()) {
      throw new BadRequestException("Card is expired.");
    }
  }

  private static Long calculateTotalPrice (CreateOrderVo request) {
    Long total = 0L;
    for (ProductRequest product : request
        .getProducts()) {
      total += product.getPrice();
    }
    return total;
  }

  private static void validateAmount (Long amountCard, Long amountPurchase ) {
    if (amountPurchase > amountCard) throw new BadRequestException("Amount unauthorized.");
  }

  private static void validateInPersonShop(CardDto card, CreateOrderVo request) {
    if (card.getType() != request
        .getPurchaseType()) {
      throw new BadRequestException("Transaction type invalid.");
    }
    Encoder.matches(request
        .getPasscode(), card.getPasscode());
  }

  private static void validateVirtualShop (CardDto card, CreateOrderVo request) {
    // validar o cvv

    if (card.getType() != CREDIT || request
        .getPurchaseType() != CREDIT) {
      throw new BadRequestException("Transaction type invalid.");
    }
  }
}
