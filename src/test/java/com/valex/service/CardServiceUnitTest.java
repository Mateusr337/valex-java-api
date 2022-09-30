package com.valex.service;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.service.domain.mother.CardMother.*;
import static com.valex.service.domain.mother.UserMother.getUser;
import static com.valex.utils.Encoder.encode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.mapper.CardMapper;
import com.valex.domain.model.Card;
import com.valex.domain.validation.CardPasscodeValidation;
import com.valex.domain.validation.CardTypeAndLimitValidation;
import com.valex.repository.CardRepository;
import com.valex.service.domain.mother.CardMother;
import com.valex.service.domain.mother.UserMother;
import com.valex.utils.Encoder;
import java.util.List;
import java.util.Optional;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardServiceUnitTest {

  @InjectMocks
  private CardService cardService;

  @Mock
  private CardRepository cardRepository;
  @Mock
  private CardMapper cardMapper;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() { openMocks(this); }

  @Test
  void WhenRequestFindAllUsersThenReturnAnUserList () {
    when(cardRepository.findAll()).thenReturn(List.of(getCardWithId(DEBIT)));
    when(cardMapper.modelToDto(anyList()))
        .thenReturn(List.of(getCardDtoWithoutId(CardType.CREDIT)));

    List<CardDto> response = cardService.findAll();

    assertEquals(1, response.size());
    assertEquals(CardDto.class, response.get(0).getClass());
  }

  @Test
  void whenFindByIdAnEmptyCardThenReturnThrowNotFound () {
    when(cardRepository.findById(anyLong())).thenReturn(getOptionalCardEmpty());

    try {
      cardService.findByIdOrFail(1L);

    } catch (Exception e) {
      assertEquals(NotFoundException.class, e.getClass());
      assertEquals("Card Not Found", e.getMessage());
    }
  }

  @Test
  void whenFindByIdAnValidCardThenReturnThisCard () {
    CardType cardType = DEBIT;
    Card card = getCardWithId(cardType);

    when(cardRepository.findById(anyLong())).thenReturn(Optional.of(card));
    when(cardMapper.modelToDto(any(Card.class))).thenReturn(getCardDtoWithId(cardType));

    CardDto response = cardService.findByIdOrFail(card.getId());

    assertEquals(CardDto.class, response.getClass());
    assertEquals(response.getId(), card.getId());
  }

  @Test
  void whenCreateValidCardThenReturnThisCard () {
    CardDto expectResponseData = getCardDtoWithId(DEBIT);

    when(userService.findByIdOrFail(anyLong())).thenReturn(getUser());
    when(cardRepository.save(any(Card.class))).thenReturn(getCardWithId(DEBIT));

    when(cardMapper.dtoToModel(any(CardDto.class))).thenReturn(getCardWithId(DEBIT));
    when(cardMapper.modelToDto(any(Card.class))).thenReturn(expectResponseData);

    CardDto response = cardService.create(getCardDtoWithoutId(DEBIT));

    assertEquals(CardDto.class, response.getClass());
    assertEquals(expectResponseData, response);
  }

  @Test
  void whenActivateValidCardThenReturnWithStatusActive () {
    String passcode = "123456";
    Card card = getCardWithId(CREDIT);
    Card cardActivated = card;
    cardActivated.setPasscode(encode(passcode));
    CardDto cardDto = getCardDtoWithId(CREDIT);
    cardDto.setPasscode(encode(passcode));

    when(cardMapper.dtoToModel(any(CardDto.class))).thenReturn(card);
    when(cardMapper.modelToDto(any(Card.class))).thenReturn(cardDto);
    when(cardRepository.findById(anyLong())).thenReturn(Optional.of(card));
    when(cardRepository.save(any(Card.class))).thenReturn(cardActivated);

    CardDto response = cardService.activate(card.getId(), passcode);

    assertEquals(CardDto.class, response.getClass());
    assertEquals(ACTIVE, cardDto.getStatus());
    assertEquals(cardDto, response);
  }

//  @Test
//  void findCardsByUserId() {
//  }

}