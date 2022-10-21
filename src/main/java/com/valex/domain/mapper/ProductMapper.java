package com.valex.domain.mapper;

import com.valex.domain.dto.ProductDto;
import com.valex.domain.model.Order;
import com.valex.domain.model.Product;
import com.valex.domain.request.ProductOrderRequest;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", ignore = true)
  ProductDto requestToDto (ProductOrderRequest productOrderRequest);

  Product dtoToModel (ProductDto productDto);

  ProductDto modelToDto (Product product);

  List<ProductDto> modelToDto (List<Product> product);
}
