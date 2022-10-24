package com.valex.service;

import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getActivatedCard;
import static com.valex.domain.mother.CardMother.getActivatedCardDto;
import static com.valex.domain.mother.CardMother.getCardDtoWithId;
import static com.valex.domain.mother.OrderMother.getCreateOrderVo;
import static com.valex.domain.mother.OrderMother.getOrder;
import static com.valex.domain.mother.OrderMother.getOrderDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.model.Product;
import com.valex.domain.mother.ProductMother;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.repository.OrderRepository;
import com.valex.repository.ProductRepository;
import com.valex.service.impl.OrderServiceImpl;
import com.valex.utils.Encoder;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceUnitTest {

  @InjectMocks
  private OrderServiceImpl orderService;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private OrderMapper orderMapper;

  @Mock
  private CardService cardService;

  @Mock
  private ProductRepository productRepository;

  @BeforeEach
  void setUp() { openMocks(this); }

  @Test
  void givenValidCreateCreditInPersonOrderThenReturnCreatedOrder () {
    Card card = getActivatedCard(CREDIT);
    Order order = getOrder(card, card.getType());
    Product product = ProductMother.getProduct(card.getId());
    OrderDto orderDto = getOrderDto(card, CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, card);

    given(cardService.findByIdOrFail(anyLong())).willReturn(getActivatedCardDto(CREDIT));
    given(orderMapper.voToModel(any())).willReturn(order);
    given(orderRepository.save(any())).willReturn(order);
    given(productRepository.saveAll(anyList())).willReturn(List.of(product));
    given(orderMapper.modelToDto(any())).willReturn(orderDto);

    OrderDto response = orderService.create(createOrderVo);

    verify(cardService).findByIdOrFail(createOrderVo.getCard().getId());
    verify(orderMapper).voToModel(createOrderVo);
    verify(orderRepository).save(order);
    verify(productRepository).saveAll(any());
    verify(orderMapper).modelToDto(order);

    then(response).isEqualTo(orderDto);
  }

  @Test
  void givenInactiveCardThenReturnBadRequest () {
    Card card = getActivatedCard(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, card);

    given(cardService.findByIdOrFail(anyLong())).willReturn(getCardDtoWithId(CREDIT));

    try {
      OrderDto response = orderService.create(createOrderVo);
      then(response.getId()).isNull();

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(BadRequestException.class);
      then(e.getMessage()).isEqualTo("Card not been activated.");
    }

  }

  @Test
  void givenExpiredCardThenReturnBadRequest () {
    Card card = getActivatedCard(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, card);

    CardDto cardDto = getActivatedCardDto(CREDIT);
    cardDto.setExpirationDate(new Date());

    given(cardService.findByIdOrFail(anyLong())).willReturn(cardDto);

    try {
      OrderDto response = orderService.create(createOrderVo);
      then(response.getId()).isNull();

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(BadRequestException.class);
      then(e.getMessage()).isEqualTo("Card is expired.");
    }

  }

  @Test
  void givenRequestTypeInvalidCardThenReturnBadRequest () {
    Card card = getActivatedCard(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(DEBIT, card);

    CardDto cardDto = getActivatedCardDto(CREDIT);
    given(cardService.findByIdOrFail(anyLong())).willReturn(cardDto);

    try {
      OrderDto response = orderService.create(createOrderVo);
      then(response.getId()).isNull();

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(BadRequestException.class);
      then(e.getMessage()).isEqualTo("Transaction type invalid.");
    }

  }

  @Test
  void givenInvalidAmountCreditCardThenReturnBadRequest () {
    Card card = getActivatedCard(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, card);

    CardDto cardDto = getActivatedCardDto(CREDIT);
    cardDto.setLimit(0L);
    given(cardService.findByIdOrFail(anyLong())).willReturn(cardDto);

    try {
      OrderDto response = orderService.create(createOrderVo);
      then(response.getId()).isNull();

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(BadRequestException.class);
      then(e.getMessage()).isEqualTo("Amount unauthorized.");
    }
  }

  @Test
  void givenInvalidAmountDebitCardThenReturnBadRequest () {
    Card card = getActivatedCard(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, card);

    CardDto cardDto = getActivatedCardDto(CREDIT);
    cardDto.setBalance(0L);
    given(cardService.findByIdOrFail(anyLong())).willReturn(cardDto);

    try {
      OrderDto response = orderService.create(createOrderVo);
      then(response.getId()).isNull();

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(BadRequestException.class);
      then(e.getMessage()).isEqualTo("Amount unauthorized.");
    }
  }

}