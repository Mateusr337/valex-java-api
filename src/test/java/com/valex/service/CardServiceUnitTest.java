package com.valex.service;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardType.*;
import static com.valex.domain.mother.CardMother.*;
import static com.valex.domain.mother.UserMother.getUser;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.mapper.impl.CardMapper;
import com.valex.domain.model.Card;
import com.valex.repository.CardRepository;
import com.valex.service.impl.CardServiceImpl;
import com.valex.service.impl.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardServiceUnitTest {

  @InjectMocks
  private CardServiceImpl cardService;

  @Mock
  private CardRepository cardRepository;
  @Mock
  private CardMapper cardMapper;

  @Mock
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() { openMocks(this); }

  @Test
  void WhenRequestFindAllUsersThenReturnAnUserList () {
    given(cardRepository.findAll()).willReturn(List.of(getCardWithId(DEBIT)));
    given(cardMapper.modelToDto(anyList()))
        .willReturn(List.of(getCardDtoWithoutId(CREDIT)));

    List<CardDto> response = cardService.findAll();

    then(response.size()).isEqualTo(1);
    then(response.get(0).getClass()).isEqualTo(CardDto.class);
  }

  @Test
  void whenFindByIdAnEmptyCardThenReturnThrowNotFound () {
    given(cardRepository.findById(anyLong())).willReturn(getOptionalCardEmpty());

    try {
      CardDto response = cardService.findByIdOrFail(1L);
      then(response.getId()).isNull();

    } catch (Exception e) {

      then(e.getClass()).isEqualTo(NotFoundException.class);
      then(e.getMessage()).isEqualTo("Card Not Found.");
    }
  }

  @Test
  void whenFindByIdAnValidCardThenReturnThisCard () {
    CardType cardType = DEBIT;
    Card card = getCardWithId(cardType);

    given(cardRepository.findById(anyLong())).willReturn(Optional.of(card));
    given(cardMapper.modelToDto(any(Card.class))).willReturn(getCardDtoWithId(cardType));

    CardDto response = cardService.findByIdOrFail(card.getId());

    then(response.getClass()).isEqualTo(CardDto.class);
    then(card.getId()).isEqualTo(response.getId());
  }

  @Test
  void whenCreateValidCardThenReturnThisCard () {
    CardDto expectResponseData = getCardDtoWithId(DEBIT);

    given(userService.findByIdOrFail(anyLong())).willReturn(getUser());
    given(cardRepository.save(any(Card.class))).willReturn(getCardWithId(DEBIT));

    given(cardMapper.dtoToModel(any(CardDto.class))).willReturn(getCardWithId(DEBIT));
    given(cardMapper.modelToDto(any(Card.class))).willReturn(expectResponseData);

    CardDto response = cardService.create(getCardDtoWithoutId(DEBIT));

    then(response.getClass()).isEqualTo(CardDto.class);
    then(response).isEqualTo(expectResponseData);
  }

  @Test
  void whenActivateValidCardThenReturnWithStatusActive () {
    String passcode = getPasscode();
    Card card = getCardWithId(CREDIT);
    Card cardActivated = getActivatedCard(CREDIT);
    CardDto cardDto = getCardDtoWithId(CREDIT);

    given(cardMapper.dtoToModel(any(CardDto.class))).willReturn(card);
    given(cardMapper.modelToDto(any(Card.class))).willReturn(cardDto);
    given(cardRepository.findById(anyLong())).willReturn(Optional.of(card));
    given(cardRepository.save(any(Card.class))).willReturn(cardActivated);

    CardDto response = cardService.activate(card.getId(), passcode);

    then(response.getClass()).isEqualTo(CardDto.class);
    then(cardDto.getStatus()).isEqualTo(ACTIVE);
    then(response).isEqualTo(cardDto);
  }

  @Test
  void whenTryActiveCardActivatedThenReturnThrowBadRequest () {
    String passcode = getPasscode();
    Card cardActivated = getActivatedCard(CREDIT);
    CardDto cardDto = getActivatedCardDto(CREDIT);

    given(cardMapper.dtoToModel(any(CardDto.class))).willReturn(cardActivated);
    given(cardMapper.modelToDto(any(Card.class))).willReturn(cardDto);
    given(cardRepository.findById(anyLong())).willReturn(Optional.of(cardActivated));

    try {
      CardDto response = cardService.activate(cardActivated.getId(), passcode);
      then(response.getId()).isNull();

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(BadRequestException.class);
      then(e.getMessage()).isEqualTo("This card already been activated.");
    }
  }

  @Test
  void whenFindCardByUserIdWithCreatedCardsThenReturnThen () {
    Card card = getCardWithId(CREDIT);
    CardDto cardDto = getCardDtoWithId(CREDIT);

    given(cardRepository.findByUserId(anyLong())).willReturn(List.of(card));
    given(cardMapper.modelToDto(anyList())).willReturn(List.of(cardDto));

    List<CardDto> response = cardService.findCardsByUserId(cardDto.getUserId());

    verify(cardMapper).modelToDto(List.of(card));

    then(response.size()).isEqualTo(1);
    then(response.get(0)).isEqualTo(cardDto);
    then(response.get(0).getClass()).isEqualTo(CardDto.class);
  }


}