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

  private static final Integer INVOICE_INITIAL_DAY = 9;
  private static final Integer INVOICE_FINAL_DAY = 9;
  private static final Integer INVOICE_VALIDATE_DAY = 18;

  @Autowired
  private CardService cardService;

  @Autowired
  private OrderService orderService;

  public InvoiceDto getInvoiceByCardIdAndMonth (Long cardId, Integer month, Integer year) {
    cardService.findByIdOrFail(cardId);
    Calendar startDate = getDate(INVOICE_INITIAL_DAY, month, year, 2);
    Calendar finalDate = getDate(INVOICE_FINAL_DAY, month, year, 1);
    Calendar paymentDate = getDate(INVOICE_VALIDATE_DAY, month, year, 1);

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
    invoiceDto.setPaymentDateLimit(paymentDate.getTime());
    return invoiceDto;
  }

  private Calendar getDate (int day, int month, int year, int decreaseMonth) {
    Calendar date = Calendar.getInstance();
    int calendarMonth = formatMonth(month, decreaseMonth);
    if (calendarMonth > month) year --;

    date.set(year, calendarMonth, day, 0, 0, 0);
    return date;
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
