package com.valex.domain.mapper;

import com.valex.domain.dto.ProductDto;
import com.valex.domain.model.Product;
import com.valex.domain.request.ProductOrderRequest;
import org.mapstruct.Mapping;

public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", ignore = true)
  ProductDto requestToDto (ProductOrderRequest productOrderRequest);

  Product dtoToModel (ProductDto productDto);
}
