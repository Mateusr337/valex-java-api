package com.valex.service;

import com.valex.domain.dto.CardDto;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.validation.CardTypeValidation;
import com.valex.repository.CardRepository;
import com.valex.utils.GenerateCardData;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final CardRepository cardRepository;
  private final UserService userService;

  public CardService(CardRepository cardRepository, UserService userService) {
    this.cardRepository = cardRepository;
    this.userService = userService;
  }

  public List<Card> getAll () {
    return this.cardRepository.findAll();
  }

  public void create (CardDto cardDto) {
    User user = this.userService.findByIdOrFail(cardDto.getUserId());
    CardTypeValidation.valid(cardDto.getType());

    //implementar validação-regra de negocio para limit(>0)-credito e limit(0)-debito

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
