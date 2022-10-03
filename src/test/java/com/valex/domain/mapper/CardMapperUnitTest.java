package com.valex.domain.mapper;

import static com.valex.domain.enumeration.CardStatus.DISABLED;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getCardRequest;
import static com.valex.domain.mother.UserMother.getUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.UserDto;
import com.valex.domain.enumeration.CardStatus;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.User;
import com.valex.domain.mother.CardMother;
import com.valex.domain.request.CardRequest;
import com.valex.service.UserService;
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
  void dtoToModel() {
  }

  @Test
  void modelToDto() {
  }

  @Test
  void testModelToDto() {
  }

  @Test
  void dtoToResponse() {
  }

  @Test
  void testDtoToResponse() {
  }
}