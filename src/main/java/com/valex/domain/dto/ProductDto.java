package com.valex.domain.dto;

import com.valex.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

  private Long id;

  private String title;

  private String description;

  private Long price;

  private Long orderId;

}
