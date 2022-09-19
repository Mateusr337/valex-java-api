package com.valex.service;

import com.valex.domain.dto.CardDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.repository.CardRepository;
import com.valex.utils.GenerateCardData;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private UserService userService;

  public List<Card> getAll () {
    return this.cardRepository.findAll();
  }

  public void create (CardDto cardDto) {
    Random random = new Random();

    User user = this.userService.findByIdOrFail(cardDto.getUserId());

    Card card = new Card ();
    card.setNumber(GenerateCardData.Number());
    card.setCvv(GenerateCardData.CVV());
    card.setStatus("disabled");
    card.setLimitCredit(cardDto.getLimit());
    card.setType(cardDto.getType());
    card.setUserName(user.getName());
    card.setUser(user);

    this.cardRepository.save(card);
  }

}
