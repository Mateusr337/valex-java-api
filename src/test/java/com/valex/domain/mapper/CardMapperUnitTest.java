package com.valex.domain.mapper;

import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getCardDtoWithId;
import static com.valex.domain.mother.CardMother.getCardRequest;
import static com.valex.domain.mother.CardMother.getCardWithId;
import static com.valex.domain.mother.UserMother.getUser;
import static com.valex.utils.Encoder.getNewEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.UserDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.mother.CardMother;
import com.valex.domain.request.CardRequest;
import com.valex.domain.response.CardResponse;
import com.valex.service.UserService;
import com.valex.utils.Encoder;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardMapperUnitTest {

  @InjectMocks
  private CardMapper cardMapper;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void WhenRequestReturnDto () {
    CardRequest cardRequest = getCardRequest(DEBIT);
    User user = getUser();
    when(userService.findByIdOrFail(anyLong())).thenReturn(user);

    CardDto response = cardMapper.requestToDto(cardRequest);

    assertEquals(CardDto.class, response.getClass());
    assertEquals(cardRequest.getUserId(), response.getUserId());
    assertEquals(cardRequest.getType(), response.getType().name());
    assertEquals(cardRequest.getLimit(), response.getLimit());
    assertNotNull(response.getCvv());
    assertNotNull(response.getNumber());
    assertEquals(user.getName(), response.getUserName());
    assertEquals(DISABLED, response.getStatus());
    assertNull(response.getPasscode());
  }

  @Test
  void whenDtoToReturnModel () {
    CardDto cardDto = getCardDtoWithId(DEBIT);
    User user = getUser();

    when(userService.findByIdOrFail(anyLong())).thenReturn(user);
    Card response = cardMapper.dtoToModel(cardDto);
    verify(userService).findByIdOrFail(user.getId());

    assertEquals(Card.class, response.getClass());
    assertEquals(cardDto.getNumber(), response.getNumber());
    assertEquals(cardDto.getCvv(), response.getCvv());
    assertEquals(cardDto.getStatus(), response.getStatus());
    assertEquals(cardDto.getLimit(), response.getLimitCredit());
    assertEquals(cardDto.getType(), response.getType());
    assertEquals(cardDto.getUserName(), response.getUserName());
    assertEquals(user, response.getUser());
  }

  @Test
  void WhenModelToReturnDto () {
    Card card = getCardWithId(CREDIT);

    CardDto response = cardMapper.modelToDto(card);

    assertEquals(CardDto.class, response.getClass());
    assertEquals(card.getNumber(), response.getNumber());
    assertEquals(card.getCvv(), response.getCvv());
    assertEquals(card.getStatus(), response.getStatus());
    assertEquals(card.getLimitCredit(), response.getLimit());
    assertEquals(card.getType(), response.getType());
    assertEquals(card.getUserName(), response.getUserName());
    assertEquals(card.getUser().getId(), response.getUserId());
  }

  @Test
  void WhenModelListToReturnDtoList () {
    Card card = getCardWithId(CREDIT);

    List<CardDto> response = cardMapper.modelToDto(List.of(card));

    assertEquals(1, response.size());
    assertEquals(CardDto.class, response.get(0).getClass());
    assertEquals(card.getNumber(), response.get(0).getNumber());
    assertEquals(card.getCvv(), response.get(0).getCvv());
    assertEquals(card.getStatus(), response.get(0).getStatus());
    assertEquals(card.getLimitCredit(), response.get(0).getLimit());
    assertEquals(card.getType(), response.get(0).getType());
    assertEquals(card.getUserName(), response.get(0).getUserName());
    assertEquals(card.getUser().getId(), response.get(0).getUserId());
  }

  @Test
  void whenDtoToReturnResponse() {
    CardDto cardDto = getCardDtoWithId(CREDIT);

    CardResponse response = cardMapper.dtoToResponse(cardDto);

    assertEquals(CardResponse.class, response.getClass());
    assertEquals(cardDto.getNumber(), response.getNumber());
    assertEquals(cardDto.getStatus(), response.getStatus());
    assertEquals(cardDto.getLimit(), response.getLimitCredit());
    assertEquals(cardDto.getType(), response.getType());
    assertEquals(cardDto.getUserName(), response.getUserName());
    assertEquals(cardDto.getUserId(), response.getUserId());
  }

  @Test
  void WhenDtoListToReturnResponseList () {
    CardDto cardDto = getCardDtoWithId(DEBIT);

    List<CardResponse> response = cardMapper.dtoToResponse(List.of(cardDto));

    assertEquals(1, response.size());
    assertEquals(CardResponse.class, response.get(0).getClass());
    assertEquals(cardDto.getNumber(), response.get(0).getNumber());
    assertEquals(cardDto.getStatus(), response.get(0).getStatus());
    assertEquals(cardDto.getLimit(), response.get(0).getLimitCredit());
    assertEquals(cardDto.getType(), response.get(0).getType());
    assertEquals(cardDto.getUserName(), response.get(0).getUserName());
    assertEquals(cardDto.getUserId(), response.get(0).getUserId());
  }
}