package com.valex.domain.mapper;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.service.UserService;
import com.valex.utils.Encoder;
import com.valex.utils.GenerateCardData;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper (componentModel = "spring")
public class CardMapper {

  @Autowired
  private UserService userService;

  public Card DtoToModel(CardDto cardDto) {
    User user = this.userService.findByIdOrFail(cardDto.getUserId());
    String encodedCVV = Encoder.encode(GenerateCardData.CVV());

    Card card = new Card ();
    card.setNumber(GenerateCardData.Number());
    card.setCvv(encodedCVV);
    card.setStatus(CardStatus.DISABLED);
    card.setLimitCredit(cardDto.getLimit());
    card.setType(CardType.valueOf(cardDto.getType()));
    card.setUserName(user.getName());
    card.setUser(user);

    return card;
  }
}
