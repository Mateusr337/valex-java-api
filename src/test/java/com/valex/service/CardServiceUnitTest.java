package com.valex.service;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.*;
import static com.valex.domain.mother.UserMother.getUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.mapper.CardMapper;
import com.valex.domain.model.Card;
import com.valex.repository.CardRepository;
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
      CardDto response = cardService.findByIdOrFail(1L);
      assertNull(response);

    } catch (Exception e) {

      assertEquals(NotFoundException.class, e.getClass());
      assertEquals("Card Not Found.", e.getMessage());
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
    String passcode = getPASSCODE();
    Card card = getCardWithId(CREDIT);
    Card cardActivated = getActivatedCard(CREDIT);
    CardDto cardDto = getCardDtoWithId(CREDIT);

    when(cardMapper.dtoToModel(any(CardDto.class))).thenReturn(card);
    when(cardMapper.modelToDto(any(Card.class))).thenReturn(cardDto);
    when(cardRepository.findById(anyLong())).thenReturn(Optional.of(card));
    when(cardRepository.save(any(Card.class))).thenReturn(cardActivated);

    CardDto response = cardService.activate(card.getId(), passcode);

    assertEquals(CardDto.class, response.getClass());
    assertEquals(ACTIVE, cardDto.getStatus());
    assertEquals(cardDto, response);
  }

  @Test
  void whenTryActiveCardActivatedThenReturnThrowBadRequest () {
    String passcode = getPASSCODE();
    Card cardActivated = getActivatedCard(CREDIT);
    CardDto cardDto = getActivatedCardDto(CREDIT);

    when(cardMapper.dtoToModel(any(CardDto.class))).thenReturn(cardActivated);
    when(cardMapper.modelToDto(any(Card.class))).thenReturn(cardDto);
    when(cardRepository.findById(anyLong())).thenReturn(Optional.of(cardActivated));

    try {
      CardDto response = cardService.activate(cardActivated.getId(), passcode);
      assertNull(response);

    } catch (Exception e) {
      assertEquals(BadRequestException.class, e.getClass());
      assertEquals("This card already is activated", e.getMessage());
    }
  }

  @Test
  void whenFindCardByUserIdWithCreatedCardsThenReturnThen () {
    Card card = getCardWithId(CREDIT);
    CardDto cardDto = getCardDtoWithId(CREDIT);

    when(cardRepository.findByUserId(anyLong())).thenReturn(List.of(card));
    when(cardMapper.modelToDto(anyList())).thenReturn(List.of(cardDto));

    List<CardDto> response = cardService.findCardsByUserId(cardDto.getUserId());

    verify(cardMapper).modelToDto(List.of(card));

    assertEquals(1, response.size());
    assertEquals(cardDto, response.get(0));
    assertEquals(CardDto.class, response.get(0).getClass());
  }


}