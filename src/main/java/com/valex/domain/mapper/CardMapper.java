package com.valex.domain.mapper;

import com.valex.domain.dto.CardDto;
import com.valex.domain.request.CardRequest;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.response.CardResponse;
import com.valex.service.UserService;
import com.valex.utils.Encoder;
import com.valex.utils.GenerateCardData;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper (componentModel = "spring")
public class CardMapper {

  @Autowired
  private UserService userService;

  public Card requestToModel(CardRequest cardRequest) {
    User user = this.userService.findByIdOrFail(cardRequest.getUserId());
    String encodedCVV = Encoder.encode(GenerateCardData.CVV());

    Card card = new Card ();
    card.setNumber(GenerateCardData.Number());
    card.setCvv(encodedCVV);
    card.setStatus(CardStatus.DISABLED);
    card.setLimitCredit(cardRequest.getLimit());
    card.setType(CardType.valueOf(cardRequest.getType()));
    card.setUserName(user.getName());
    card.setUser(user);

    return card;
  }

  public List<CardDto> modelToDto (List<Card> cardList) {
    List<CardDto> cardDtoList = new ArrayList<>();

    for (int i = 0; i < cardList.size(); i++) {
      Card card = cardList.get(i);

      CardDto cardDto = new CardDto();
      cardDto.setId(card.getId());
      cardDto.setCvv(card.getCvv());
      cardDto.setType(card.getType());
      cardDto.setNumber(card.getNumber());
      cardDto.setUserId(card.getUser().getId());
      cardDto.setLimitCredit(card.getLimitCredit());
      cardDto.setPasscode(card.getPasscode());
      cardDtoList.set(i, cardDto);
    };
    return cardDtoList;
  }

  public CardDto modelToDto (Card card) {
    CardDto cardDto = new CardDto();

    cardDto.setId(card.getId());
    cardDto.setCvv(card.getCvv());
    cardDto.setType(card.getType());
    cardDto.setNumber(card.getNumber());
    cardDto.setUserId(card.getUser().getId());
    cardDto.setLimitCredit(card.getLimitCredit());
    cardDto.setPasscode(card.getPasscode());

    return cardDto;
  }

  public List<CardResponse> dtoToResponse (List<CardDto> cardDtoList) {
    List<CardResponse> CardResponseList = new ArrayList<>();

    for (int i = 0; i < cardDtoList.size(); i++) {
      CardDto cardDto = cardDtoList.get(i);

      CardResponse cardResponse = new CardResponse();
      cardResponse.setId(cardDto.getId());
      cardResponse.setCvv(cardDto.getCvv());
      cardResponse.setType(cardDto.getType());
      cardResponse.setNumber(cardDto.getNumber());
      cardResponse.setUserId(cardDto.getUserId());
      cardResponse.setLimitCredit(cardDto.getLimitCredit());
      CardResponseList.set(i, cardResponse);
    };
    return CardResponseList;
  }

  public CardResponse dtoToResponse (CardDto cardDto) {
    CardResponse cardResponse = new CardResponse();

    cardResponse.setId(cardDto.getId());
    cardResponse.setCvv(cardDto.getCvv());
    cardResponse.setType(cardDto.getType());
    cardResponse.setNumber(cardDto.getNumber());
    cardResponse.setUserId(cardDto.getUserId());
    cardResponse.setLimitCredit(cardDto.getLimitCredit());

    return cardResponse;
  }

}
