package com.valex.service;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.model.Order;
import com.valex.domain.model.User;
import com.valex.domain.vo.CreateOrderVo;
import java.util.List;

public interface OrderService {

  OrderDto create (CreateOrderVo createOrderVo);

  void delete (Long id);

}
