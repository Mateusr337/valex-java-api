package com.valex.utils;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.dto.ProductDto;
import com.valex.domain.response.OrderResponse;
import com.valex.domain.response.ProductResponse;
import java.util.List;

public final class TotalOrderListPrice {

  public static Long calculateDto (List<OrderDto> orders) {
    Long sum = 0L;

    for (OrderDto order : orders) {
      for (ProductDto product : order.getProducts()) {
        sum += product.getPrice();
      }
    }
    return sum;
  }

  public static Long calculateResponse (List<OrderResponse> orders) {
    Long sum = 0L;

    for (OrderResponse order : orders) {
      for (ProductResponse product : order.getProducts()) {
        sum += product.getPrice();
      }
    }
    return sum;
  }
}
