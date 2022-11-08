package com.valex.controller;

import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getActivatedCardDto;
import static com.valex.domain.mother.InvoiceMother.getInvoiceDto;
import static com.valex.domain.mother.InvoiceMother.getInvoiceResponse;
import static com.valex.domain.mother.OrderMother.getOrderDto;
import static com.valex.domain.mother.OrderMother.getOrderResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.mapper.InvoiceMapper;
import com.valex.domain.response.InvoiceResponse;
import com.valex.domain.response.OrderResponse;
import com.valex.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerUnitTest {

  private final static String BASE_URL = "/invoices";

  @InjectMocks
  private InvoiceController invoiceController;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private InvoiceService invoiceService;

  @MockBean
  private InvoiceMapper invoiceMapper;

  @Test
  @WithMockUser
  void givenGetInvoiceByCardIdAndBetweenDateThenReturnInvoiceResponse () throws Exception {
    CardDto cardDto = getActivatedCardDto(DEBIT);
    OrderDto orderDto = getOrderDto(cardDto, cardDto.getType());
    OrderResponse orderResponse = getOrderResponse(cardDto.getId(), cardDto.getType());
    InvoiceDto invoiceDto = getInvoiceDto(orderDto);
    InvoiceResponse invoiceResponse = getInvoiceResponse(orderResponse);

    given(invoiceService.getInvoiceByCardIdAndMonth(anyLong(), anyInt(), anyInt()))
        .willReturn(invoiceDto);
    given(invoiceMapper.dtoToResponse(any(InvoiceDto.class)))
        .willReturn(invoiceResponse);

    String url = BASE_URL+ "/cards/2/months/10/years/2022";

    mvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalPrice").value(invoiceDto.getTotalPrice()));

    verify(invoiceMapper).dtoToResponse(invoiceDto);

  }
}
