package com.valex.service.impl;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.dto.ProductDto;
import com.valex.service.CardService;
import com.valex.service.InvoiceService;
import com.valex.service.OrderService;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

  @Autowired
  private CardService cardService;

  @Autowired
  private OrderService orderService;

  public InvoiceDto getInvoiceByCardIdAndMonth (Long cardId, Integer month, Integer year) {
    cardService.findByIdOrFail(cardId);
    Calendar startDate = getStartDate(month, year);
    Calendar finalDate = getFinalDate(month, year);

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
    invoiceDto.setPaymentDateLimit(formatPaymentLimitDate(finalDate).getTime());
    return invoiceDto;
  }

  private Calendar getStartDate (Integer month, Integer year) {
    Calendar startDate = Calendar.getInstance();
    int monthStartDate = formatMonth(month, 2);
    if (monthStartDate > month) year --;

    startDate.set(year, monthStartDate, 10, 0, 0, 0);
    return startDate;
  }

  private Calendar getFinalDate (Integer month, Integer year) {
    Calendar finalDate = Calendar.getInstance();
    int monthFinalDate = formatMonth(month, 1);
    if (monthFinalDate > month) year --;

    finalDate.set(year, monthFinalDate, 9, 0, 0, 0);
    return finalDate;
  }

  private Calendar formatPaymentLimitDate (Calendar finalDate) {
    Calendar paymentLimitDate = finalDate;
    paymentLimitDate.set(DAY_OF_MONTH, 18);
    paymentLimitDate.set(HOUR_OF_DAY, 23);
    paymentLimitDate.set(MINUTE, 59);
    paymentLimitDate.set(SECOND, 59);
    return paymentLimitDate;
  }

  private Long calculateInvoiceTotalPrice (List<OrderDto> orders) {
    if (orders.size() == 0) return 0L;

    Long totalPrice = 0L;
    for (OrderDto order: orders) {
      for (ProductDto product: order.getProducts()) {
        totalPrice += product.getPrice();
      }
    }
    return totalPrice;
  }

  private int formatMonth(int month, int decreaseMonth) {
    month -= decreaseMonth;
    if (month < 0) { month += 12; }
    return month;
  }
}
