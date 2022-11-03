package com.valex.service.impl;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.dto.ProductDto;
import com.valex.domain.model.Order;
import com.valex.service.CardService;
import com.valex.service.InvoiceService;
import com.valex.service.OrderService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

  @Autowired
  private CardService cardService;

  @Autowired
  private OrderService orderService;

  public InvoiceDto getInvoiceByMonth (Long cardId, Integer month) {
    CardDto card = cardService.findByIdOrFail(cardId);
    Calendar startDate = getStartDate(month);
    Calendar finalDate = getFinalDate(month);
    Calendar paymentLimitDate = formatPaymentLimitDate(finalDate);

    List<OrderDto> orders = orderService.findOrderByPeriodAndCardId(
        cardId,
        startDate.getTime(),
        finalDate.getTime()
    );

    InvoiceDto invoiceDto = new InvoiceDto();
    invoiceDto.setOrders(orders);
    invoiceDto.setTotalPrice(calculateInvoiceTotalPrice(orders));
    invoiceDto.setStartDate(startDate.getTime());
    invoiceDto.setEndDate(finalDate.getTime());
    invoiceDto.setPaymentDateLimit(paymentLimitDate.getTime());

    return invoiceDto;
  }

  private Calendar getStartDate (Integer month) {
    Calendar startDate = Calendar.getInstance();
    startDate.set(Calendar.MONTH, month - 2);
    startDate.set(Calendar.DAY_OF_MONTH, 10);
    startDate.set(Calendar.HOUR_OF_DAY, 0);
    return startDate;
  }

  private Calendar getFinalDate (Integer month) {
    Calendar finalDate = Calendar.getInstance();
    finalDate.set(Calendar.MONTH, month - 1);
    finalDate.set(Calendar.DAY_OF_MONTH, 9);
    finalDate.set(Calendar.HOUR_OF_DAY, 0);
    return finalDate;
  }

  private Calendar formatPaymentLimitDate (Calendar finalDate) {
    Calendar paymentLimitDate = finalDate;
    paymentLimitDate.set(Calendar.DAY_OF_MONTH, 18);
    return paymentLimitDate;
  }

  private Long calculateInvoiceTotalPrice(List<OrderDto> orders) {
    Long totalPrice = 0L;
    for (OrderDto order: orders) {
      for (ProductDto product: order.getProducts()) {
        totalPrice += product.getPrice();
      }
    }
    return totalPrice;
  }
}
