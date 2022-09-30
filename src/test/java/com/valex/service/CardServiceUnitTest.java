package com.valex.service;

import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.service.domain.mother.CardMother.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardType;
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
  void whenFindByIdAnValidCardThenReturnThis () {
    CardType cardType = DEBIT;

    Optional<Card> optionalCardDebit = getOptionalCardValid(cardType);
    when(cardRepository.findById(anyLong())).thenReturn(optionalCardDebit);
    when(cardMapper.modelToDto(any(Card.class))).thenReturn(getCardDtoWithId(cardType));

    CardDto response = cardService.findByIdOrFail(1L);

    assertEquals(CardDto.class, response.getClass());
    assertEquals(optionalCardDebit.get().getId(), response.getId());
  }

//  @Test
//  void findByIdOrFail() {
//  }
//
//  @Test
//  void create() {
//  }
//
//  @Test
//  void activate() {
//  }
//
//  @Test
//  void findCardsByUserId() {
//  }

}