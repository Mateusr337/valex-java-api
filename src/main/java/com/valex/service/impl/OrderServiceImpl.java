package com.valex.service.impl;

import static com.valex.domain.enumeration.CardStatus.ACTIVE;
import static com.valex.utils.Encoder.matches;

import com.valex.domain.dto.CardDto;
import com.valex.domain.exception.BadRequestException;
import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.validation.ValidateCardToCreateOrder;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.repository.OrderRepository;
import com.valex.service.CardService;
import com.valex.service.OrderService;
import com.valex.utils.Encoder;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CardService cardService;

  @Autowired
  private OrderMapper orderMapper;

  public void create (CreateOrderVo createOrderVo) {
    CardDto cardDto = cardService.findByIdOrFail(createOrderVo.getCard().getId());
    ValidateCardToCreateOrder.valid(cardDto, createOrderVo);

    matches(createOrderVo.getPasscode(), cardDto.getPasscode());

    System.out.println(createOrderVo);

//    Order order = orderMapper.dtoToModel(createOrderVo);
//    return orderMapper.modelToDto(orderRepository.save(order));
  }





  public List<Order> findAll() {
    return null;
  }

  public void delete(Long id) {}
}
