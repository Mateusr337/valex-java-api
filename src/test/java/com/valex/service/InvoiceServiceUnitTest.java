package com.valex.service;

import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.mother.CardMother.getActivatedCardDto;
import static com.valex.domain.mother.OrderMother.getOrderDto;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.dto.OrderDto;
import com.valex.service.impl.InvoiceServiceImpl;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InvoiceServiceUnitTest {

  @InjectMocks
  private InvoiceServiceImpl invoiceService;

  @Mock
  private CardService cardService;

  @Mock
  private OrderService orderService;

  @BeforeEach
  void setUp() { openMocks(this); }

  @Test
  void whenGetInvoiceByCardIdAndMonthThenReturnInvoiceDto () {
    CardDto cardDto = getActivatedCardDto(DEBIT);
    OrderDto orderDto = getOrderDto(cardDto, cardDto.getType());

    given(cardService.findByIdOrFail(any())).willReturn(cardDto);
    given(orderService.findOrderByPeriodAndCardId(anyLong(), any(), any())).willReturn(List.of(orderDto));

    InvoiceDto response = invoiceService.getInvoiceByCardIdAndMonth(cardDto.getId(), 11, 2021);

    verify(cardService).findByIdOrFail(cardDto.getId());
    then(response.getClass()).isEqualTo(InvoiceDto.class);
  }
}
