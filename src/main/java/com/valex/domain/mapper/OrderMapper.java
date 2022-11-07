package com.valex.domain.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.dto.ProductDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.model.Product;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.response.OrderResponse;
import com.valex.domain.vo.CreateOrderVo;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface OrderMapper {
  
  CreateOrderVo requestToVo (CreateOrderRequest createOrderRequest);

  @Mapping(target = "id", ignore = true)
  Order voToModel (CreateOrderVo createOrderVo);

  OrderDto modelToDto (Order order);

  List<OrderDto> modelToDto (List<Order> orders);

  OrderResponse dtoToResponse (OrderDto orderDto);

  List<OrderResponse> dtoToResponse (List<OrderDto> orderDtoList);

  @AfterMapping
  default void setCardIdRequestToVo (
      @MappingTarget CreateOrderVo createOrderVo,
      CreateOrderRequest createOrderRequest
  ) {
    CardDto cardDto = new CardDto();
    cardDto.setId(createOrderRequest.getCardId());
    createOrderVo.setCard(cardDto);
  }

  @AfterMapping
  default void setCardIdDtoToResponse (
      @MappingTarget OrderResponse orderResponse,
      OrderDto orderDto
  ) { orderResponse.setCardId(orderDto.getCard().getId()); }

  @AfterMapping
  default void setCardIdDtoListToResponseList (
      @MappingTarget List<OrderResponse> orderResponseList,
      List<OrderDto> orderDtoList
  ) {
    for (int i = 0; i < orderDtoList.size(); i++) {
      orderResponseList.get(i).setCardId(orderDtoList.get(i).getCard().getId());
    }
  }
}
