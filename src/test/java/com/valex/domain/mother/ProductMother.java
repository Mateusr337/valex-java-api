package com.valex.domain.mother;

import com.valex.domain.dto.OrderDto;
import com.valex.domain.dto.ProductDto;
import com.valex.domain.model.Order;
import com.valex.domain.model.Product;
import com.valex.domain.request.ProductRequest;
import com.valex.domain.response.ProductResponse;

public final class ProductMother {

  private static final String TITLE = "smartphone";

  private static final String DESCRIPTION = "smartphone 64gb";

  private static final Long PRICE = 100000L;

  private static final Long ID = 10L;

  public static ProductRequest getProductsOrderRequest () {
    ProductRequest productOrderRequest = new ProductRequest();
    productOrderRequest.setTitle(TITLE);
    productOrderRequest.setDescription(DESCRIPTION);
    productOrderRequest.setPrice(PRICE);

    return productOrderRequest;
  }

  public static Product getProduct (Long orderId) {
    Product product = new Product();

    Order order = new Order();
    order.setId(orderId);

    product.setId(ID);
    product.setTitle(TITLE);
    product.setDescription(DESCRIPTION);
    product.setPrice(PRICE);
    product.setOrder(order);

    return product;
  }

  public static ProductDto getProductDto (Long orderId) {
    ProductDto product = new ProductDto();

    product.setId(ID);
    product.setTitle(TITLE);
    product.setDescription(DESCRIPTION);
    product.setPrice(PRICE);
    product.setOrderId(orderId);

    return product;
  }

  public static ProductResponse getProductResponse (Long orderId) {
    ProductResponse product = new ProductResponse();

    product.setId(ID);
    product.setTitle(TITLE);
    product.setDescription(DESCRIPTION);
    product.setPrice(PRICE);
    product.setOrderId(orderId);

    return product;
  }

}
