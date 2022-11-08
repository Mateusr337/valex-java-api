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
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.InvoiceDto;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    CardDto cardDto = getActivatedCardDto(card.getType());
    Order order = getOrder(card, card.getType());
    Product product = ProductMother.getProduct(card.getId());
    OrderDto orderDto = getOrderDto(cardDto, CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, cardDto);

    given(cardService.findByIdOrFail(anyLong())).willReturn(getActivatedCardDto(CREDIT));
    given(orderMapper.voToModel(any())).willReturn(order);
    given(orderRepository.save(any())).willReturn(order);
    given(productRepository.saveAll(anyList())).willReturn(List.of(product));
    given(orderMapper.modelToDto(any(Order.class))).willReturn(orderDto);

    OrderDto response = orderService.create(createOrderVo);

    verify(cardService).findByIdOrFail(createOrderVo.getCard().getId());
    verify(orderMapper).voToModel(createOrderVo);
    verify(orderRepository).save(order);
    verify(productRepository).saveAll(any());
    verify(orderMapper).modelToDto(order);

    then(response).isEqualTo(orderDto);
  }

  @Test
  void givenValidCreateDebitInPersonOrderThenReturnCreatedOrder () {
    Long amount = 1000000000L;
    Card card = getActivatedCard(DEBIT, amount);
    CardDto cardDto = getActivatedCardDto(card.getType(), amount);
    Order order = getOrder(card, card.getType());
    Product product = ProductMother.getProduct(card.getId());
    OrderDto orderDto = getOrderDto(cardDto, card.getType());
    CreateOrderVo createOrderVo = getCreateOrderVo(card.getType(), cardDto);

    given(cardService.findByIdOrFail(anyLong())).willReturn(cardDto);
    given(orderMapper.voToModel(any())).willReturn(order);
    given(orderRepository.save(any())).willReturn(order);
    given(productRepository.saveAll(anyList())).willReturn(List.of(product));
    given(orderMapper.modelToDto(any(Order.class))).willReturn(orderDto);

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
    CardDto cardDto = getActivatedCardDto(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, cardDto);

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
    CardDto cardDto = getActivatedCardDto(CREDIT);
    cardDto.setExpirationDate(new Date());
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, cardDto);

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
    CardDto cardDto = getActivatedCardDto(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(DEBIT, cardDto);

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
    CardDto cardDto = getActivatedCardDto(CREDIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(CREDIT, cardDto);

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
    CardDto cardDto = getActivatedCardDto(DEBIT);
    CreateOrderVo createOrderVo = getCreateOrderVo(DEBIT, cardDto);

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

  @Test
  void givenValidIdThenDeleteOrder () {
    Card card = getActivatedCard(DEBIT);
    Order order = getOrder(card, card.getType());
    given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));
    doNothing().when(orderRepository).deleteById(order.getId());

    orderService.delete(order.getId());

    verify(orderRepository).findById(order.getId());
  }

  @Test
  void givenInvalidIdThenReturnThrow () {
    Card card = getActivatedCard(DEBIT);
    Order order = getOrder(card, card.getType());
    given(orderRepository.findById(order.getId())).willReturn(Optional.empty());

    doNothing().when(orderRepository).deleteById(order.getId());

    try {
      orderService.delete(order.getId());
    } catch (Exception e) {
      then(e.getClass()).isEqualTo(NotFoundException.class);
      then(e.getMessage()).isEqualTo("Order Not Found.");
    }
  }

  @Test
  void givenFindOrderByPeriodAndCardIdThenReturnOrderListDto () {
    Card card = getActivatedCard(DEBIT);
    CardDto cardDto = getCardDtoWithId(card.getType());
    Order order = getOrder(card, card.getType());
    OrderDto orderDto = getOrderDto(cardDto, cardDto.getType());

    Date date = new Date();

    given(orderRepository.findByCardIdAndByDateBetween(
        anyLong(), any(Date.class), any(Date.class)))
        .willReturn(List.of(order));

    given(orderMapper.modelToDto(anyList()))
        .willReturn(List.of(orderDto));

    List<OrderDto> response = orderService.findOrderByPeriodAndCardId(
        card.getId(), date, date);

    verify(orderRepository).
        findByCardIdAndByDateBetween(card.getId(), date, date);
    verify(orderMapper).modelToDto(List.of(order));

    then(response.get(0)).isEqualTo(orderDto);
  }
}
