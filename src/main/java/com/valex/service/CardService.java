package com.valex.service;

import com.valex.domain.dto.CardDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.repository.CardRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.jetbrains.annotations.NotNull;
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
    StringBuilder cardNumber = new StringBuilder();
    StringBuilder CardCVV = new StringBuilder();


    for (int i = 0; i < 16; i++) {
      int randomNumber = random.nextInt(9);
      cardNumber.append(Integer.toString(randomNumber));
    }

    for (int i = 0; i < 3; i++) {
      int randomNumber = random.nextInt(9);
      CardCVV.append(Integer.toString(randomNumber));
    }

    User user = this.userService.findByIdOrFail(cardDto.getUserId());

    Card card = new Card ();
    card.setNumber(cardNumber.toString());
    card.setCvv(CardCVV.toString());
    card.setStatus("disabled");
    card.setLimitCredit(cardDto.getLimit());
    card.setType(cardDto.getType());
    card.setUserName("fulano");
    card.setUser(user);

    this.cardRepository.save(card);
  }

}
