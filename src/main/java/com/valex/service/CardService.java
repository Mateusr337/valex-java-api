package com.valex.service;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.validation.CardPasscodeValidation;
import com.valex.domain.validation.CardTypeAndLimitValidation;
import com.valex.repository.CardRepository;
import com.valex.utils.Encoder;
import com.valex.utils.GenerateCardData;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private UserService userService;



  public List<Card> findAll() {
    return this.cardRepository.findAll();
  }

  public Card findByIdOrFail(Long id) {
    Optional<Card> card = this.cardRepository.findById(id);

    if (card.isEmpty()) {
      throw new NotFoundException("Not found card!");
    }
    return card.get();
  }

  public void create (CardDto cardDto) {
    User user = this.userService.findByIdOrFail(cardDto.getUserId());

    CardTypeAndLimitValidation.valid(cardDto.getType(), cardDto.getLimit());
    String encodedCVV = Encoder.encode(GenerateCardData.CVV());

    Card card = new Card ();
    card.setNumber(GenerateCardData.Number());
    card.setCvv(encodedCVV);
    card.setStatus(CardStatus.DISABLED);
    card.setLimitCredit(cardDto.getLimit());
    card.setType(CardType.valueOf(cardDto.getType()));
    card.setUserName(user.getName());
    card.setUser(user);

    this.cardRepository.save(card);
  }

  public void activate (Long cardId, String passcode) {
    Card card = this.findByIdOrFail(cardId);
    CardPasscodeValidation.valid(passcode);
    String encodedPasscode = Encoder.encode(passcode);

    if (card.getStatus().equals(CardStatus.ACTIVE)) {
      throw new BadRequestException("This card is already activated");
    }

    card.setStatus(CardStatus.ACTIVE);
    card.setPasscode(encodedPasscode);

    this.cardRepository.save(card);
  }

  public List<Card> findCardsByUserId (Long id) {
    return this.cardRepository.findByUserId(id);
  }

}
