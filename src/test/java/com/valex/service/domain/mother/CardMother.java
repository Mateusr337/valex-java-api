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
import java.util.Date;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class CardMother {

  private static final Long ID = 5L;
  private static final User USER = getUser();
  private static final String PASSCODE = "1234";
  private static final String CVV = "123";
  private static final String NUMBER = "5198668954545286";
  private static final Date VALIDATE = new Date();

  public static CardDto getCardDtoWithoutId (CardType cardType) {
    CardDto cardDto = new CardDto();

    cardDto.setUserId(null);
    cardDto.setUserId(USER.getId());
    cardDto.setUserName(USER.getName());
    cardDto.setNumber(NUMBER);
    cardDto.setCvv(getEncodedCVV());
    cardDto.setStatus(DISABLED);
    cardDto.setPasscode(null);
    cardDto.setType(cardType);
    cardDto.setLimit(getCardLimit(cardType));

    return cardDto;
  }

  public static CardDto getCardDtoWithId (CardType cardType) {
    CardDto cardDto = new CardDto();

    cardDto.setId(ID);
    cardDto.setUserId(USER.getId());
    cardDto.setUserName(USER.getName());
    cardDto.setNumber(NUMBER);
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

  public static Card getCardWithId (CardType cardType) {
    Card card = new Card();

    card.setId(ID);
    card.setUser(USER);
    card.setUserName(USER.getName());
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
    return encode(PASSCODE);
  }

  private static String getEncodedCVV () {
    return encode(CVV);
  }
}

