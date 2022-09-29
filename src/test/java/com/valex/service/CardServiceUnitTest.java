package com.valex.service;

import static com.valex.service.domain.mother.CardMother.getCardDtoWithoutId;
import static com.valex.service.domain.mother.CardMother.getCardWithId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.mapper.CardMapper;
import com.valex.repository.CardRepository;
import java.util.List;
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
    when(cardRepository.findAll()).thenReturn(List.of(getCardWithId(CardType.DEBIT)));
    when(cardMapper.modelToDto(anyList()))
        .thenReturn(List.of(getCardDtoWithoutId(CardType.CREDIT)));

    List<CardDto> response = cardService.findAll();

    assertEquals(1, response.size());
    assertEquals(CardDto.class, response.get(0).getClass());
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