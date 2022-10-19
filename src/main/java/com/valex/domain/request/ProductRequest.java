package com.valex.domain.request;

import com.valex.domain.model.Order;
import javax.validation.constraints.NotBlank;

public class ProductRequest {

  @NotBlank (message = "title cannot be null")
  private String title;

  private String description;

  private Long price;

  private Order order;

}
