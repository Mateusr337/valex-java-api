package com.valex.Factory;


import static com.valex.domain.mother.OrderMother.getOrder;
import static com.valex.domain.mother.OrderMother.getOrderWithoutId;

import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.mother.OrderMother;
import com.valex.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderFactory {

  @Autowired
  private OrderRepository repository;

  public Order createOrderInTheDatabase (Card card) {
    Order order = getOrderWithoutId(card, card.getType());
    return repository.save(order);
  }

}
