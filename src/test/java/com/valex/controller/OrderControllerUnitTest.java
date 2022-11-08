package com.valex.controller;

import static com.valex.domain.mother.OrderMother.getCreateOrderRequest;
import static com.valex.domain.mother.OrderMother.getCreateOrderVo;
import static com.valex.domain.mother.OrderMother.getOrderDto;
import static com.valex.domain.mother.OrderMother.getOrderResponse;

import static com.valex.domain.enumeration.CardType.*;
import static com.valex.domain.mother.CardMother.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.model.Card;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.response.OrderResponse;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerUnitTest {

  private final static String BASE_URL = "/orders";

  @InjectMocks
  private OrderController orderController;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private OrderServiceImpl orderService;

  @MockBean
  private OrderMapper orderMapper;

  @Test
  @WithMockUser
  void givenCreateCreditOrderRequestReturnCreatedOrder () throws Exception {
    CardDto cardDto = getActivatedCardDto(CREDIT);
    CreateOrderRequest createOrderRequest = getCreateOrderRequest(cardDto.getId(), cardDto.getType());
    CreateOrderVo createOrderVo = getCreateOrderVo(cardDto.getType(), cardDto);
    OrderDto orderDto = getOrderDto(cardDto, cardDto.getType());
    OrderResponse orderResponse = getOrderResponse(cardDto.getId(), cardDto.getType());

    given(orderMapper.requestToVo(any())).willReturn(createOrderVo);
    given(orderMapper.dtoToResponse(any(OrderDto.class))).willReturn(orderResponse);
    given(orderService.create(any())).willReturn(orderDto);

    mvc.perform(post(BASE_URL)
        .content(new ObjectMapper().writeValueAsString(createOrderRequest))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(orderResponse.getId()));

    verify(orderMapper).requestToVo(any(CreateOrderRequest.class));
    verify(orderService).create(any(CreateOrderVo.class));
    verify(orderMapper).dtoToResponse(any(OrderDto.class));
  }

  @Test
  @WithMockUser
  void givenCreateDebitOrderRequestReturnCreatedOrder () throws Exception {
    CardDto cardDto = getActivatedCardDto(DEBIT);
    CreateOrderRequest createOrderRequest = getCreateOrderRequest(cardDto.getId(), cardDto.getType());
    CreateOrderVo createOrderVo = getCreateOrderVo(cardDto.getType(), cardDto);
    OrderDto orderDto = getOrderDto(cardDto, cardDto.getType());
    OrderResponse orderResponse = getOrderResponse(cardDto.getId(), cardDto.getType());

    given(orderMapper.requestToVo(any())).willReturn(createOrderVo);
    given(orderMapper.dtoToResponse(any(OrderDto.class))).willReturn(orderResponse);
    given(orderService.create(any())).willReturn(orderDto);

    mvc.perform(post(BASE_URL)
            .content(new ObjectMapper().writeValueAsString(createOrderRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(orderResponse.getId()));

    verify(orderMapper).requestToVo(any(CreateOrderRequest.class));
    verify(orderService).create(any(CreateOrderVo.class));
    verify(orderMapper).dtoToResponse(any(OrderDto.class));
  }

  @Test
  @WithMockUser
  void givenDeleteOrderRequestThenCallOderServiceDelete () throws Exception {
    Long id = 10L;

    doNothing().when(orderService).delete(id);

    mvc.perform(delete(BASE_URL + "/" + id))
        .andExpect(status().isNoContent());
  }

}