package com.valex.domain.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.model.Order;
import com.valex.domain.request.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface OrderMapper {

  @Mapping (target = "id", ignore = true)
  OrderDto requestToDto (OrderRequest orderRequest);

  Order dtoToModel (OrderDto orderDto);

  OrderDto modelToDto (Order order);
}
