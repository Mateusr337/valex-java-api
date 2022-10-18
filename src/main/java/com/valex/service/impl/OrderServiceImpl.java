package com.valex.service.impl;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.model.Order;
import com.valex.repository.OrderRepository;
import com.valex.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderMapper orderMapper;

  public OrderDto create (OrderDto orderDto) {
    Order order = orderMapper.dtoToModel(orderDto);
//    return orderMapper.modelToDto(orderRepository.save(order));
    return orderDto;
  }

  public List<Order> findAll() {
    return null;
  }

  public Order findByIdOrFail(Long id) {
    return null;
  }

  public void delete(Long id) {

  }
}
