package com.valex.domain.mapper;

import com.valex.domain.request.CardRequest;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.service.UserService;
import com.valex.utils.Encoder;
import com.valex.utils.GenerateCardData;
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
}
