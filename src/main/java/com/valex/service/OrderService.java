package com.valex.service;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.dto.UserDto;
import com.valex.domain.model.Order;
import com.valex.domain.model.User;
import java.util.List;

public interface OrderService {

  List<Order> findAll();

  Order findByIdOrFail (Long id);

  OrderDto create (OrderDto orderDto);

  void delete (Long id);

}
