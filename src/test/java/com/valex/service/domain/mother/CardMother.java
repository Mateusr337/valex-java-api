package com.valex.service.domain.mother;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.service.domain.mother.UserMother.getUser;
import static com.valex.utils.Encoder.encode;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.utils.GenerateCardData;
import java.util.Optional;

public final class CardMother {

  public static CardDto getCardDtoWithoutId (CardType cardType) {
    CardDto cardDto = new CardDto();

    cardDto.setUserId(2L);
    cardDto.setUserName("Fulano");
    cardDto.setNumber(GenerateCardData.Number());
    cardDto.setCvv(getEncodedCVV());
    cardDto.setStatus(DISABLED);
    cardDto.setPasscode(null);
    cardDto.setType(cardType);
    cardDto.setLimit(getCardLimit(cardType));

    return cardDto;
  }

  public static CardDto getCardDtoWithId (CardType cardType) {
    CardDto cardDto = new CardDto();

    cardDto.setId(5L);
    cardDto.setUserId(2L);
    cardDto.setUserName("Fulano");
    cardDto.setNumber(GenerateCardData.Number());
    cardDto.setCvv(getEncodedCVV());
    cardDto.setStatus(DISABLED);
    cardDto.setPasscode(null);
    cardDto.setType(cardType);
    cardDto.setLimit(getCardLimit(cardType));

    return cardDto;
  }

  public static CardDto getActivatedCardDto (CardType cardType) {
    CardDto cardDto = getCardDtoWithId(cardType);
    cardDto.setPasscode(getEncodedPasscode());
    cardDto.setStatus(ACTIVE);
    return cardDto;
  }

  public static Card getActivatedCard (CardType cardType) {
    Card cardDto = getCardWithId(cardType);
    cardDto.setPasscode(getEncodedPasscode());
    cardDto.setStatus(ACTIVE);
    return cardDto;
  }

  public static Optional<Card> getOptionalCardEmpty () {
    return Optional.of(new Card());
  }

  public static Optional<Card> getOptionalCardValid (CardType cardType) {
    Card card = getCardWithId(cardType);
    return Optional.of(card);
  }

  public static Card getCardWithId (CardType cardType) {
    Card card = new Card();
    User user = getUser();

    card.setId(5L);
    card.setUser(user);
    card.setUserName(user.getName());
    card.setNumber(GenerateCardData.Number());
    card.setCvv(getEncodedCVV());
    card.setStatus(DISABLED);
    card.setPasscode(null);
    card.setType(cardType);
    card.setLimitCredit(getCardLimit(cardType));

    return card;
  }

  private static Long getCardLimit (CardType cardType) {
    if (cardType == CardType.CREDIT) return 5000000L;
    if (cardType == CardType.DEBIT) return 0L;
    return null;
  }

  private static String getEncodedPasscode() {
    return encode("1234");
  }

  private static String getEncodedCVV () {
    return encode(GenerateCardData.CVV());
  }
}

