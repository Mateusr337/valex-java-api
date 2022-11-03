package com.valex.domain.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

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
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface OrderMapper {
  
  CreateOrderVo requestToVo (CreateOrderRequest createOrderRequest);

  @Mapping(target = "id", ignore = true)
  Order voToModel (CreateOrderVo createOrderVo);

//  @Mapping (target = "products", ignore = true)
  OrderDto modelToDto (Order order);

  List<OrderDto> modelToDto (List<Order> orders);

  @Mapping (target = "cardId", ignore = true)
  OrderResponse dtoToResponse (OrderDto orderDto);

  @AfterMapping
  default void setCard (
      @MappingTarget CreateOrderVo createOrderVo,
      CreateOrderRequest createOrderRequest
  ) {
    Card card = new Card();
    card.setId(createOrderRequest.getCardId());
    createOrderVo.setCard(card);
  }

  @AfterMapping
  default void setCardId (
      @MappingTarget OrderResponse orderResponse,
      OrderDto orderDto
  ) {
    orderResponse.setCardId(orderDto.getCard().getId());
  }

  @AfterMapping
  default void setProductsDto (
      @MappingTarget Order order,
      OrderDto orderDto
  ) {
    List<ProductDto> products = new ArrayList<>();
    for (Product product : order.getProducts() ) {

      ProductDto productDto = new ProductDto();
      productDto.setId(product.getId());
      productDto.setTitle(product.getTitle());
      productDto.setDescription(product.getDescription());
      productDto.setPrice(product.getPrice());
      products.add(productDto);
    }
    orderDto.setProducts(products);

  }
}
