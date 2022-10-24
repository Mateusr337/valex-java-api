package com.valex.domain.mother;

import com.valex.domain.model.Order;
import com.valex.domain.model.Product;
import com.valex.domain.request.ProductOrderRequest;

public final class ProductMother {

  private static final String TITLE = "smartphone";

  private static final String DESCRIPTION = "smartphone 64gb";

  private static final Long PRICE = 100000L;

  private static final Long ID = 10L;

  public static ProductOrderRequest getProductsOrderRequest () {
    ProductOrderRequest productOrderRequest = new ProductOrderRequest();
    productOrderRequest.setTitle(TITLE);
    productOrderRequest.setDescription(DESCRIPTION);
    productOrderRequest.setPrice(PRICE);

    return productOrderRequest;
  }

  public static Product getProduct (Long cardId) {
    Product product = new Product();

    Order order = new Order();
    order.setId(cardId);

    product.setId(ID);
    product.setTitle(TITLE);
    product.setDescription(DESCRIPTION);
    product.setPrice(PRICE);
    product.setOrder(order);

    return product;
  }

}
