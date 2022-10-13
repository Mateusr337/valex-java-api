package com.valex.domain.mapper;

import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getCardDtoWithId;
import static com.valex.domain.mother.CardMother.getCardRequest;
import static com.valex.domain.mother.CardMother.getCardWithId;
import static com.valex.domain.mother.UserMother.getUser;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.request.CardRequest;
import com.valex.domain.response.CardResponse;
import com.valex.service.UserService;
import java.util.List;
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
    given(userService.findByIdOrFail(anyLong())).willReturn(user);

    CardDto response = cardMapper.requestToDto(cardRequest);

    then(response.getClass()).isEqualTo(CardDto.class);
    then(response.getNumber()).isNotEmpty();
    then(response.getStatus()).isEqualTo(DISABLED);
    then(response.getLimit()).isEqualTo(cardRequest.getLimit());
    then(response.getType().name()).isEqualTo(cardRequest.getType());
    then(response.getUserName()).isEqualTo(user.getName());
    then(response.getUserId()).isEqualTo(cardRequest.getUserId());
    then(response.getPasscode()).isNull();
  }

  @Test
  void whenDtoToReturnModel () {
    CardDto cardDto = getCardDtoWithId(DEBIT);
    User user = getUser();

    given(userService.findByIdOrFail(anyLong())).willReturn(user);
    Card response = cardMapper.dtoToModel(cardDto);

    then(response.getClass()).isEqualTo(Card.class);
    then(response.getNumber()).isEqualTo(cardDto.getNumber());
    then(response.getStatus()).isEqualTo(cardDto.getStatus());
    then(response.getLimitCredit()).isEqualTo(cardDto.getLimit());
    then(response.getType()).isEqualTo(cardDto.getType());
    then(response.getUserName()).isEqualTo(cardDto.getUserName());
    then(response.getUser().getId()).isEqualTo(cardDto.getUserId());

    verify(userService).findByIdOrFail(user.getId());
  }

  @Test
  void WhenModelToReturnDto () {
    Card card = getCardWithId(CREDIT);

    CardDto response = cardMapper.modelToDto(card);

    then(response.getClass()).isEqualTo(CardDto.class);
    then(response.getNumber()).isEqualTo(card.getNumber());
    then(response.getStatus()).isEqualTo(card.getStatus());
    then(response.getLimit()).isEqualTo(card.getLimitCredit());
    then(response.getType()).isEqualTo(card.getType());
    then(response.getUserName()).isEqualTo(card.getUserName());
    then(response.getUserId()).isEqualTo(card.getUser().getId());
  }

  @Test
  void WhenModelListToReturnDtoList () {
    Card card = getCardWithId(CREDIT);

    List<CardDto> response = cardMapper.modelToDto(List.of(card));

    then(response.get(0).getClass()).isEqualTo(CardDto.class);
    then(response.get(0).getNumber()).isEqualTo(card.getNumber());
    then(response.get(0).getStatus()).isEqualTo(card.getStatus());
    then(response.get(0).getLimit()).isEqualTo(card.getLimitCredit());
    then(response.get(0).getType()).isEqualTo(card.getType());
    then(response.get(0).getUserName()).isEqualTo(card.getUserName());
    then(response.get(0).getUserId()).isEqualTo(card.getUser().getId());
    then(response.get(0).getCvv()).isEqualTo(card.getCvv());
  }

  @Test
  void whenDtoToReturnResponse() {
    CardDto cardDto = getCardDtoWithId(CREDIT);

    CardResponse response = cardMapper.dtoToResponse(cardDto);

    then(response.getClass()).isEqualTo(CardResponse.class);
    then(response.getNumber()).isEqualTo(cardDto.getNumber());
    then(response.getStatus()).isEqualTo(cardDto.getStatus());
    then(response.getLimitCredit()).isEqualTo(cardDto.getLimit());
    then(response.getType()).isEqualTo(cardDto.getType());
    then(response.getUserName()).isEqualTo(cardDto.getUserName());
    then(response.getUserId()).isEqualTo(cardDto.getUserId());
  }

  @Test
  void WhenDtoListToReturnResponseList () {
    CardDto cardDto = getCardDtoWithId(DEBIT);

    List<CardResponse> response = cardMapper.dtoToResponse(List.of(cardDto));

    then(response.size()).isEqualTo(1);
    then(response.get(0).getClass()).isEqualTo(CardResponse.class);
    then(response.get(0).getNumber()).isEqualTo(cardDto.getNumber());
    then(response.get(0).getStatus()).isEqualTo(cardDto.getStatus());
    then(response.get(0).getLimitCredit()).isEqualTo(cardDto.getLimit());
    then(response.get(0).getType()).isEqualTo(cardDto.getType());
    then(response.get(0).getUserName()).isEqualTo(cardDto.getUserName());
    then(response.get(0).getUserId()).isEqualTo(cardDto.getUserId());
  }
}