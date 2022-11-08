package com.valex.controller;

import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.exception.BadRequestException;
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

  @GetMapping ("/cards/{cardId}/months/{month}/years/{year}")
  @ResponseStatus (code = HttpStatus.OK)
  public InvoiceResponse getInvoiceByCardIdAndBetweenDate (
      @PathVariable ("cardId") Long cardId,
      @PathVariable ("month") Integer month,
      @PathVariable ("year") Integer year
  ) {
    validateDates(month, year);
    InvoiceDto invoiceDto = invoiceService.getInvoiceByCardIdAndMonth(cardId, month, year);
    return invoiceMapper.dtoToResponse(invoiceDto);
  }

  private void validateDates (Integer month, Integer year) {
    if (month < 1 || month > 12) {
      throw new BadRequestException("Query param [ month ] must be between 0 and 12.");
    }
    if (year < 1000 || year >= 10000) {
      throw new BadRequestException("Query param [ year ] invalid.");
    }
  }

}
