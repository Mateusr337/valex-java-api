package com.valex.domain.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.valex.domain.dto.ProductDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.Product;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.request.ProductRequest;
import com.valex.domain.response.ProductResponse;
import com.valex.domain.vo.CreateOrderVo;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ProductMapper {

  ProductDto requestToDto (ProductRequest productRequest);

  Product dtoToModel (ProductDto productDto);

  @InheritInverseConfiguration
  ProductDto modelToDto (Product product);

  @InheritInverseConfiguration
  List<ProductDto> modelToDto (List<Product> product);

  List<ProductResponse> dtoToResponse (List<ProductDto> productDtoList);

//  @AfterMapping
//  default void setOrderId (
//      @MappingTarget ProductDto dto,
//      ProductResponse response
//  ) {
//    response.setOrderId(dto.getOrder().getId());
//  }

}
