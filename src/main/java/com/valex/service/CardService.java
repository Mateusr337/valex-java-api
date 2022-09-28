package com.valex.service;

import com.valex.domain.dto.CardDto;
import com.valex.domain.request.CardRequest;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.validation.CardPasscodeValidation;
import com.valex.domain.validation.CardTypeAndLimitValidation;
import com.valex.domain.mapper.CardMapper;
import com.valex.repository.CardRepository;
import com.valex.utils.Encoder;
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

  @Autowired
  private CardMapper cardMapper;

  public List<CardDto> findAll() {
    List<Card> cards =  this.cardRepository.findAll();
    return cardMapper.modelToDto(cards);
  }

  public Card findByIdOrFail(Long id) {
    Optional<Card> card = this.cardRepository.findById(id);

    if (card.isEmpty()) {
      throw new NotFoundException("{card.not.found}");
    }
    return card.get();
  }

  public CardDto create (CardRequest cardRequest) {
    this.userService.findByIdOrFail(cardRequest.getUserId());
    CardTypeAndLimitValidation.valid(cardRequest.getType(), cardRequest.getLimit());

    Card card = this.cardRepository.save(cardMapper.requestToModel(cardRequest));
    return cardMapper.modelToDto(card);
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

  public List<CardDto> findCardsByUserId (Long id) {
    List<Card> cardList =  this.cardRepository.findByUserId(id);
    return cardMapper.modelToDto(cardList);
  }

}
