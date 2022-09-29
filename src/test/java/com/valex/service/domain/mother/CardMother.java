package com.valex.service.domain.mother;

import static com.valex.service.domain.mother.UserMother.getUser;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.utils.Encoder;
import com.valex.utils.GenerateCardData;

public class CardMother {

  public static CardDto getCardDtoWithoutId (CardType cardType) {
    CardDto cardDto = new CardDto();

    cardDto.setUserId(2L);
    cardDto.setUserName("Fulano");
    cardDto.setNumber(GenerateCardData.Number());
    cardDto.setCvv(getEncodedCVV());
    cardDto.setStatus(CardStatus.DISABLED);
    cardDto.setPasscode(getEncodedPasscode());
    cardDto.setType(cardType);
    cardDto.setLimit(getCardLimit(cardType));

    return cardDto;
  }

  public CardDto getCardDtoWithId (CardType cardType) {
    CardDto cardDto = new CardDto();

    cardDto.setId(5L);
    cardDto.setUserId(2L);
    cardDto.setUserName("Fulano");
    cardDto.setNumber(GenerateCardData.Number());
    cardDto.setCvv(getEncodedCVV());
    cardDto.setStatus(CardStatus.DISABLED);
    cardDto.setPasscode(getEncodedPasscode());
    cardDto.setType(cardType);
    cardDto.setLimit(getCardLimit(cardType));

    return cardDto;
  }

  public static Card getCardWithId (CardType cardType) {
    Card card = new Card();
    User user = getUser();

    card.setId(5L);
    card.setUser(user);
    card.setUserName(user.getName());
    card.setNumber(GenerateCardData.Number());
    card.setCvv(getEncodedCVV());
    card.setStatus(CardStatus.DISABLED);
    card.setPasscode(getEncodedPasscode());
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
    return Encoder.encode("1234");
  }

  private static String getEncodedCVV () {
    return Encoder.encode(GenerateCardData.CVV());
  }
}

