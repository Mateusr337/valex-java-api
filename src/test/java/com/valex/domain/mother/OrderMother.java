package com.valex.domain.mother;

import static com.valex.domain.mother.ProductMother.getProduct;
import static com.valex.domain.mother.ProductMother.getProductsOrderRequest;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.request.ProductOrderRequest;
import com.valex.domain.vo.CreateOrderVo;
import java.util.Date;
import java.util.List;

public final class OrderMother {
  private static final String SHOP_NAME = "bar do manoel";

  private static final String PASSCODE = "1234";

  private static final Long ID = 8L;

  public static CreateOrderVo getCreateOrderVo (CardType type, Card card) {
    ProductOrderRequest product = getProductsOrderRequest();
    CreateOrderVo createOrderVo = new CreateOrderVo();

    createOrderVo.setCard(card);
    createOrderVo.setPurchaseType(type);
    createOrderVo.setShopName(SHOP_NAME);
    createOrderVo.setPasscode(PASSCODE);
    createOrderVo.setProducts(List.of(product));
    createOrderVo.setDate(new Date());

    return createOrderVo;
  }

  public static CreateOrderRequest getCreateOrderRequest (Long cardId, CardType type) {
    ProductOrderRequest product = getProductsOrderRequest();
    CreateOrderRequest createOrderRequest = new CreateOrderRequest();

    createOrderRequest.setCardId(cardId);
    createOrderRequest.setPurchaseType(type.name());
    createOrderRequest.setPasscode(PASSCODE);
    createOrderRequest.setShopName(SHOP_NAME);
    createOrderRequest.setProducts(List.of(product));

    return createOrderRequest;
  }

  public static Order getOrder (Card card, CardType type) {
    Order order = new Order();

    order.setId(ID);
    order.setCard(card);
    order.setPurchaseType(type);
    order.setDate(new Date());
    order.setShopName(SHOP_NAME);
    order.setProducts(List.of(getProduct(card.getId())));

    return order;
  }

  public static OrderDto getOrderDto (Card card, CardType type) {
    OrderDto orderDto = new OrderDto();

    orderDto.setId(ID);
    orderDto.setCard(card);
    orderDto.setPurchaseType(type);
    orderDto.setDate(new Date());
    orderDto.setShopName(SHOP_NAME);
    orderDto.setProducts(List.of(getProduct(card.getId())));

    return orderDto;
  }
}
