package com.valex.controller;

import static com.valex.domain.enumeration.CardType.DEBIT;

import com.valex.Factory.CardFactory;
import com.valex.Factory.OrderFactory;
import com.valex.Factory.UserFactory;
import com.valex.domain.model.Card;
import com.valex.domain.model.Order;
import com.valex.domain.model.User;
import java.util.Calendar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

@Sql(value = "/clean-database.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class InvoiceControllerIntegrationTest {

  private static final String BASE_URL = "/invoices";

  @Autowired
  private InvoiceController invoiceController;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserFactory userFactory;

  @Autowired
  private CardFactory cardFactory;

  @Autowired
  private OrderFactory orderFactory;

  @Test
  @WithMockUser
  void givenGetInvoiceByCardIdAndBetweenDate () throws Exception {
    User user = userFactory.createUserInTheDatabase();
    Card card = cardFactory.createActivatedCardInTheDatabase(DEBIT, user);
    Order order = orderFactory.createOrderInTheDatabase(card);

    Calendar c = Calendar.getInstance();
    String url = BASE_URL + "/cards/" + card.getId() +
        "/months/" + (c.get(MONTH) + 1) + "/years/" + c.get(YEAR);

    mvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orders").isArray())
        .andExpect(jsonPath("$.orders[0].id").value(order.getId()));

  }

}
