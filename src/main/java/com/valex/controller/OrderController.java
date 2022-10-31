package com.valex.controller;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.response.OrderResponse;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.service.OrderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public OrderResponse create (@RequestBody @Valid CreateOrderRequest createOrderRequest) {
    CreateOrderVo orderVo = orderMapper.requestToVo(createOrderRequest);
    OrderDto orderDto = orderService.create(orderVo);
    return orderMapper.dtoToResponse(orderDto);
  }

  @DeleteMapping ("/{id}")
  @ResponseStatus (code = HttpStatus.NO_CONTENT)
  public void delete (@PathVariable Long id) {
    orderService.delete(id);
  }
}
