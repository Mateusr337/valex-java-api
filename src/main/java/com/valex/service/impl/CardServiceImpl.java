package com.valex.service.impl;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.utils.Encoder.encode;
import static java.lang.String.valueOf;

import com.valex.domain.dto.CardDto;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.Card;
import com.valex.domain.validation.CardPasscodeValidation;
import com.valex.domain.validation.CardTypeAndLimitCardRequestValidation;
import com.valex.domain.mapper.impl.CardMapper;
import com.valex.repository.CardRepository;
import com.valex.service.CardService;
import java.util.Calendar;
import java.util.Date;
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
    CardTypeAndLimitCardRequestValidation.valid(valueOf(cardDto.getType()), cardDto.getLimit());

    cardDto.setExpirationDate(generateExpirationDate());
    cardDto.setBalance(0L);

    Card card = cardRepository.save(cardMapper.dtoToModel(cardDto));
    return cardMapper.modelToDto(card);
  }

  public CardDto activate (Long cardId, String passcode) {
    CardDto cardDto = findByIdOrFail(cardId);
    CardPasscodeValidation.valid(passcode);

    if (cardDto.getStatus().equals(ACTIVE)) {
      throw new BadRequestException("This card already been activated.");
    }
    cardDto.setStatus(ACTIVE);
    cardDto.setPasscode(encode(passcode));


    Card card = cardRepository.save(cardMapper.dtoToModel(cardDto));
    return cardMapper.modelToDto(card);
  }

  public List<CardDto> findCardsByUserId (Long id) {
    List<Card> cardList =  cardRepository.findByUserId(id);
    return cardMapper.modelToDto(cardList);
  }

  private Date generateExpirationDate () {
    Calendar cal = Calendar.getInstance();
    cal.setTime( new Date() );
    cal.add(Calendar.YEAR, 5);

    return cal.getTime();
  }

}
