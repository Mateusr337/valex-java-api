package com.valex.Factory;

import com.valex.controller.CardController;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.mother.CardMother;
import com.valex.domain.request.CardRequest;
import com.valex.domain.response.CardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardFactory {

  @Autowired
  private CardController cardController;

  public CardResponse createCardInTheDatabase (CardType type) {
    CardRequest cardRequest = CardMother.getCardRequest(type);
    return cardController.create(cardRequest);
  }
}
