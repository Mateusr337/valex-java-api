package com.valex.controller;

import static com.valex.domain.enumeration.CardType.CREDIT;
import static com.valex.domain.mother.OrderMother.getCreateOrderRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.Factory.CardFactory;
import com.valex.Factory.UserFactory;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.mapper.impl.CardMapper;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.request.CreateOrderRequest;
import com.valex.domain.response.CardResponse;
import com.valex.domain.response.OrderResponse;
import com.valex.domain.response.UserResponse;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class OrderControllerIntegrationTest {

  private static final String BASE_URL = "/orders";

  @Autowired
  private OrderController orderController;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserFactory userFactory;

  @Autowired
  private CardFactory cardFactory;

  @Test
  @WithMockUser
  void givenCreateOrderRequestReturnCreatedOrder () throws Exception {
    User user = userFactory.createUserInTheDatabase();
    Card card = cardFactory.createActivatedCardInTheDatabase(CREDIT, user);

    CreateOrderRequest createOrderRequest = getCreateOrderRequest(card.getId(), card.getType());

    mvc.perform(post(BASE_URL)
            .content(new ObjectMapper().writeValueAsString(createOrderRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

}
