package com.valex.domain.mother;

import static com.valex.domain.mother.OrderMother.getOrderDto;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.response.InvoiceResponse;
import com.valex.domain.response.OrderResponse;
import com.valex.utils.TotalOrderListPrice;
import java.util.Date;
import java.util.List;

public final class InvoiceMother {

  public static InvoiceDto getInvoiceDto (OrderDto orderDto) {
    Long totalPriceOrders = TotalOrderListPrice.calculateDto(List.of(orderDto));

    InvoiceDto invoiceDto = new InvoiceDto();
    invoiceDto.setTotalPrice(totalPriceOrders);
    invoiceDto.setStartDate(new Date());
    invoiceDto.setEndDate(new Date());
    invoiceDto.setPaymentDateLimit(new Date());
    invoiceDto.setOrders(List.of(orderDto));

    return invoiceDto;
  }

  public static InvoiceResponse getInvoiceResponse (OrderResponse orderResponse) {
    Long totalPriceOrders = TotalOrderListPrice.calculateResponse(List.of(orderResponse));

    InvoiceResponse invoiceDto = new InvoiceResponse();
    invoiceDto.setTotalPrice(totalPriceOrders);
    invoiceDto.setStartDate(new Date());
    invoiceDto.setEndDate(new Date());
    invoiceDto.setPaymentDateLimit(new Date());
    invoiceDto.setOrders(List.of(orderResponse));

    return invoiceDto;
  }
}
