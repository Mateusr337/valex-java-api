package com.valex.service;

import static com.valex.utils.Encoder.encode;
import static java.lang.String.valueOf;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.Card;
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

  public CardDto findByIdOrFail(Long id) {
    Optional<Card> card = this.cardRepository.findById(id);

    if (card.isEmpty()) {
      throw new NotFoundException("{card.not.found}");
    }
    return cardMapper.modelToDto(card.get());
  }

  public CardDto create (CardDto cardDto) {
    this.userService.findByIdOrFail(cardDto.getUserId());
    CardTypeAndLimitValidation.valid(valueOf(cardDto.getType()), cardDto.getLimit());

    Card card = this.cardRepository.save(cardMapper.dtoToModel(cardDto));
    return cardMapper.modelToDto(card);
  }

  public CardDto activate (Long cardId, String passcode) {
    CardDto cardDto = this.findByIdOrFail(cardId);
    CardPasscodeValidation.valid(passcode);

    if (cardDto.getStatus().equals(CardStatus.ACTIVE)) {
      throw new BadRequestException("This card already is activated");
    }
    cardDto.setStatus(CardStatus.ACTIVE);
    cardDto.setPasscode(encode(passcode));

    Card card = this.cardRepository.save(cardMapper.dtoToModel(cardDto));
    return cardMapper.modelToDto(card);
  }

  public List<CardDto> findCardsByUserId (Long id) {
    List<Card> cardList =  this.cardRepository.findByUserId(id);
    return cardMapper.modelToDto(cardList);
  }

}
