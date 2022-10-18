package com.valex.domain.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.vo.CreateOrderVo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface OrderMapper {

  CreateOrderVo requestToVo (CreateOrderRequest createOrderRequest);

  Order voToModel (CreateOrderVo createOrderVo);

//  OrderDto modelToDto (Order order);

  @AfterMapping
  default void setCard(@MappingTarget CreateOrderVo createOrderVo, CreateOrderRequest createOrderRequest) {
    Card card = new Card();
    card.setId(createOrderRequest.getCardId());
    createOrderVo.setCard(card);
  }
}
