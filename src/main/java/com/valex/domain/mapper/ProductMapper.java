package com.valex.domain.mapper;

import com.valex.domain.dto.ProductDto;
import com.valex.domain.model.Product;
import com.valex.domain.request.ProductOrderRequest;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapping;

public interface ProductMapper {
  ProductDto requestToDto (ProductOrderRequest productOrderRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", ignore = true)
  Product requestToModel (ProductOrderRequest productOrderRequest);

  Product dtoToModel (ProductDto productDto);
}
