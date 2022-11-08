package com.valex.domain.dto;

import com.valex.domain.model.Order;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceDto {

  private Long totalPrice;
  private Date paymentDateLimit;
  private Date startDate;
  private Date endDate;
  private List<OrderDto> orders;

}
