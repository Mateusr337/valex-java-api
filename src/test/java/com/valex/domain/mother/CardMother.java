package com.valex.domain.mother;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.mother.UserMother.getUser;
import static com.valex.utils.Encoder.encode;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.request.CardRequest;
import com.valex.domain.response.CardResponse;
import com.valex.utils.GenerateCardData;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public final class CardMother {

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
    cardDto.setBalance(0L);
    cardDto.setLimit(getCardLimit(cardType));
    cardDto.setExpirationDate(generateExpirationDate());

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
    cardDto.setBalance(0L);
    cardDto.setLimit(getCardLimit(cardType));
    cardDto.setExpirationDate(generateExpirationDate());

    return cardDto;
  }

  public static CardDto getActivatedCardDto (CardType cardType) {
    CardDto cardDto = getCardDtoWithId(cardType);
    cardDto.setPasscode(getEncodedPasscode());
    cardDto.setStatus(ACTIVE);
    cardDto.setBalance(0L);
    return cardDto;
  }

  public static CardDto getActivatedCardDto (CardType cardType, Long balance) {
    CardDto cardDto = getCardDtoWithId(cardType);
    cardDto.setPasscode(getEncodedPasscode());
    cardDto.setStatus(ACTIVE);
    cardDto.setBalance(balance);
    return cardDto;
  }

  public static Card getActivatedCard (CardType cardType) {
    Card card = getCardWithId(cardType);
    card.setPasscode(getEncodedPasscode());
    card.setStatus(ACTIVE);
    card.setBalance(0L);
    card.setExpirationDate(generateExpirationDate());
    return card;
  }

  public static Card getActivatedCard (CardType cardType, Long amount) {
    Card card = getCardWithId(cardType);
    card.setPasscode(getEncodedPasscode());
    card.setStatus(ACTIVE);
    card.setBalance(amount);
    card.setExpirationDate(generateExpirationDate());
    return card;
  }

  public static Optional<Card> getOptionalCardEmpty () {
    return Optional.empty();
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
    card.setExpirationDate(generateExpirationDate());
    card.setBalance(0L);

    return card;
  }

  public static CardRequest getCardRequest (CardType cardType) {
    CardRequest cardRequest = new CardRequest();
    User user = getUser();

    cardRequest.setUserId(user.getId());
    cardRequest.setType(cardType.name());
    cardRequest.setLimit(getCardLimit(cardType));

    return cardRequest;
  }

  public static CardResponse getCardResponse (CardType type, CardStatus status) {
    CardResponse cardResponse = new CardResponse();
    User user = getUser();

    cardResponse.setId(ID);
    cardResponse.setUserId(user.getId());
    cardResponse.setUserName(user.getName());
    cardResponse.setNumber(NUMBER);
    cardResponse.setStatus(status);
    cardResponse.setType(type);
    cardResponse.setLimitCredit(getCardLimit(type));
    cardResponse.setBalance(0L);

    return cardResponse;
  }

  private static Long getCardLimit (CardType cardType) {
    if (cardType == CardType.CREDIT) return 5000000L;
    if (cardType == CardType.DEBIT) return 0L;
    return null;
  }

  private static Date generateExpirationDate () {
    Calendar cal = Calendar.getInstance();
    cal.setTime( new Date() );
    cal.add(Calendar.YEAR, 5);

    return cal.getTime();
  }

  private static String getEncodedPasscode() {
    return encode(PASSCODE);
  }

  private static String getEncodedCVV () {
    return encode(CVV);
  }

  public static String getPasscode() {
    return PASSCODE;
  }
}

