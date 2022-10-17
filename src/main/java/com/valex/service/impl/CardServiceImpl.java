package com.valex.service.impl;

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
import com.valex.service.CardService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private CardMapper cardMapper;

  public List<CardDto> findAll() {
    List<Card> cards =  cardRepository.findAll();
    return cardMapper.modelToDto(cards);
  }

  public CardDto findByIdOrFail(Long id) {
    Optional<Card> card = cardRepository.findById(id);

    if (card.isEmpty()) {
      throw new NotFoundException("Card Not Found.");
    }
    return cardMapper.modelToDto(card.get());
  }

  public CardDto create (CardDto cardDto) {
    userService.findByIdOrFail(cardDto.getUserId());
    CardTypeAndLimitValidation.valid(valueOf(cardDto.getType()), cardDto.getLimit());

    Card card = cardRepository.save(cardMapper.dtoToModel(cardDto));
    return cardMapper.modelToDto(card);
  }

  public CardDto activate (Long cardId, String passcode) {
    CardDto cardDto = findByIdOrFail(cardId);
    CardPasscodeValidation.valid(passcode);

    if (cardDto.getStatus().equals(CardStatus.ACTIVE)) {
      throw new BadRequestException("This card already is activated");
    }
    cardDto.setStatus(CardStatus.ACTIVE);
    cardDto.setPasscode(encode(passcode));

    Card card = cardRepository.save(cardMapper.dtoToModel(cardDto));
    return cardMapper.modelToDto(card);
  }

  public List<CardDto> findCardsByUserId (Long id) {
    List<Card> cardList =  cardRepository.findByUserId(id);
    return cardMapper.modelToDto(cardList);
  }

}
