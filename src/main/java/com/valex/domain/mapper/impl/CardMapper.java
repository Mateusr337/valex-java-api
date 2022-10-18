package com.valex.domain.mapper.impl;

import com.valex.domain.dto.CardDto;
import com.valex.domain.request.CardRequest;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.response.CardResponse;
import com.valex.service.impl.UserServiceImpl;
import com.valex.utils.Encoder;
import com.valex.utils.GenerateCardData;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper (componentModel = "spring")
public class CardMapper {

  @Autowired
  private UserServiceImpl userService;

  public CardDto requestToDto(CardRequest cardRequest) {
    User user = this.userService.findByIdOrFail(cardRequest.getUserId());
    String encodedCVV = Encoder.encode(GenerateCardData.CVV());

    CardDto cardDto = new CardDto();
    cardDto.setNumber(GenerateCardData.Number());
    cardDto.setCvv(encodedCVV);
    cardDto.setStatus(CardStatus.DISABLED);
    cardDto.setLimit(cardRequest.getLimit());
    cardDto.setType(CardType.valueOf(cardRequest.getType()));
    cardDto.setUserName(user.getName());
    cardDto.setUserId(cardRequest.getUserId());

    return cardDto;
  }

  public Card dtoToModel(CardDto cardDto) {
    User user = this.userService.findByIdOrFail(cardDto.getUserId());

    Card card = new Card();
    card.setId(cardDto.getId());
    card.setNumber(cardDto.getNumber());
    card.setCvv(cardDto.getCvv());
    card.setStatus(cardDto.getStatus());
    card.setLimitCredit(cardDto.getLimit());
    card.setType(cardDto.getType());
    card.setUserName(user.getName());
    card.setUser(user);
    card.setExpirationDate(cardDto.getExpirationDate());

    return card;
  }

  public List<CardDto> modelToDto (List<Card> cardList) {
    List<CardDto> cardDtoList = new ArrayList<>();

    for (Card card : cardList) {
      CardDto cardDto = new CardDto();
      cardDto.setId(card.getId());
      cardDto.setCvv(card.getCvv());
      cardDto.setUserName(card.getUser().getName());
      cardDto.setType(card.getType());
      cardDto.setStatus(card.getStatus());
      cardDto.setNumber(card.getNumber());
      cardDto.setUserId(card.getUser().getId());
      cardDto.setLimit(card.getLimitCredit());
      cardDto.setPasscode(card.getPasscode());
      cardDto.setExpirationDate(card.getExpirationDate());
      cardDtoList.add(cardDto);
    }
    return cardDtoList;
  }

  public CardDto modelToDto (Card card) {
    CardDto cardDto = new CardDto();

    cardDto.setId(card.getId());
    cardDto.setCvv(card.getCvv());
    cardDto.setStatus(card.getStatus());
    cardDto.setType(card.getType());
    cardDto.setNumber(card.getNumber());
    cardDto.setUserId(card.getUser().getId());
    cardDto.setUserName(card.getUserName());
    cardDto.setLimit(card.getLimitCredit());
    cardDto.setPasscode(card.getPasscode());
    cardDto.setExpirationDate(card.getExpirationDate());

    return cardDto;
  }

  public List<CardResponse> dtoToResponse (List<CardDto> cardDtoList) {
    List<CardResponse> CardResponseList = new ArrayList<>();

    for (CardDto cardDto : cardDtoList) {
      CardResponse cardResponse = new CardResponse();
      cardResponse.setId(cardDto.getId());
      cardResponse.setUserName(cardDto.getUserName());
      cardResponse.setType(cardDto.getType());
      cardResponse.setStatus(cardDto.getStatus());
      cardResponse.setNumber(cardDto.getNumber());
      cardResponse.setUserId(cardDto.getUserId());
      cardResponse.setLimitCredit(cardDto.getLimit());
      cardResponse.setExpirationDate(cardDto.getExpirationDate());
      CardResponseList.add(cardResponse);
    }
    ;
    return CardResponseList;
  }

  public CardResponse dtoToResponse (CardDto cardDto) {
    CardResponse cardResponse = new CardResponse();

    cardResponse.setId(cardDto.getId());
    cardResponse.setUserName(cardDto.getUserName());
    cardResponse.setType(cardDto.getType());
    cardResponse.setStatus(cardDto.getStatus());
    cardResponse.setNumber(cardDto.getNumber());
    cardResponse.setUserId(cardDto.getUserId());
    cardResponse.setLimitCredit(cardDto.getLimit());
    cardResponse.setExpirationDate(cardDto.getExpirationDate());

    return cardResponse;
  }

}
