package com.valex.controller;

import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.service.OrderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderMapper orderMapper;

  @PostMapping
  @ResponseStatus (code = HttpStatus.CREATED)
  public void create (@RequestBody @Valid CreateOrderRequest createOrderRequest) {
    orderService.create(orderMapper.requestToVo(createOrderRequest));
  }
}
