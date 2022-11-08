package com.valex.domain.response;

import com.valex.domain.dto.OrderDto;
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
public class InvoiceResponse {

  private Long totalPrice;
  private Date paymentDateLimit;
  private Date startDate;
  private Date endDate;
  private List<OrderResponse> orders;

}
