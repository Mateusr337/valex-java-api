package com.valex.service;

import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.model.Order;
import java.util.List;

public interface InvoiceService {

  InvoiceDto getInvoiceByMonth(Long cardId, Integer month);

}
