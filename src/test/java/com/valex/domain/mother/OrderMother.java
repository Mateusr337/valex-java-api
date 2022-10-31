package com.valex.domain.mother;

import static com.valex.domain.mother.ProductMother.getProduct;
import static com.valex.domain.mother.ProductMother.getProductDto;
import static com.valex.domain.mother.ProductMother.getProductResponse;
import static com.valex.domain.mother.ProductMother.getProductsOrderRequest;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.enumeration.CardType;
import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.request.ProductRequest;
import com.valex.domain.response.OrderResponse;
import com.valex.domain.vo.CreateOrderVo;
import java.util.Date;
import java.util.List;

public final class OrderMother {
  private static final String SHOP_NAME = "bar do manoel";

  private static final String PASSCODE = "1234";

  private static final Long ID = 8L;

  public static CreateOrderVo getCreateOrderVo (CardType purchaseType, Card card) {
    ProductRequest product = getProductsOrderRequest();
    CreateOrderVo createOrderVo = new CreateOrderVo();

    createOrderVo.setCard(card);
    createOrderVo.setPurchaseType(purchaseType);
    createOrderVo.setShopName(SHOP_NAME);
    createOrderVo.setPasscode(PASSCODE);
    createOrderVo.setProducts(List.of(product));
    createOrderVo.setDate(new Date());

    return createOrderVo;
  }

  public static CreateOrderRequest getCreateOrderRequest (Long cardId, CardType purchaseType) {
    ProductRequest product = getProductsOrderRequest();
    CreateOrderRequest createOrderRequest = new CreateOrderRequest();

    createOrderRequest.setCardId(cardId);
    createOrderRequest.setPurchaseType(purchaseType.name());
    createOrderRequest.setPasscode(PASSCODE);
    createOrderRequest.setShopName(SHOP_NAME);
    createOrderRequest.setProducts(List.of(product));

    return createOrderRequest;
  }

  public static Order getOrder (Card card, CardType purchaseType) {
    Order order = new Order();

    order.setId(ID);
    order.setCard(card);
    order.setPurchaseType(purchaseType);
    order.setDate(new Date());
    order.setShopName(SHOP_NAME);
    order.setProducts(List.of(getProduct(card.getId())));

    return order;
  }

  public static OrderDto getOrderDto (Card card, CardType purchaseType) {
    OrderDto orderDto = new OrderDto();

    orderDto.setId(ID);
    orderDto.setCard(card);
    orderDto.setPurchaseType(purchaseType);
    orderDto.setDate(new Date());
    orderDto.setShopName(SHOP_NAME);
    orderDto.setProducts(List.of(getProductDto(card.getId())));

    return orderDto;
  }

  public static OrderResponse getOrderResponse (Card card, CardType purchaseType) {
    OrderResponse orderResponse = new OrderResponse();

    orderResponse.setId(ID);
    orderResponse.setCardId(card.getId());
    orderResponse.setPurchaseType(purchaseType);
    orderResponse.setDate(new Date());
    orderResponse.setShopName(SHOP_NAME);
    orderResponse.setProducts(List.of(getProductResponse(card.getId())));

    return orderResponse;
  }
}
