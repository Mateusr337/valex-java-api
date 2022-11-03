package com.valex.controller;

import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.mapper.InvoiceMapper;
import com.valex.domain.model.Order;
import com.valex.domain.response.InvoiceResponse;
import com.valex.service.InvoiceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/invoices")
public class InvoiceController {

  @Autowired
  private InvoiceService invoiceService;

  @Autowired
  private InvoiceMapper invoiceMapper;

  @GetMapping ("/cards/{cardId}/months/{month}")
  @ResponseStatus (code = HttpStatus.OK)
  public InvoiceResponse getInvoiceByMonth (
      @PathVariable ("cardId") Long cardId,
      @PathVariable ("month") Integer month
  ) {
    InvoiceDto invoice = invoiceService.getInvoiceByMonth(cardId, month);
    return invoiceMapper.dtoToResponse(invoice);
  }

}
