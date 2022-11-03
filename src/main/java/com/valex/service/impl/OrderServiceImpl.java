package com.valex.service.impl;

import static com.valex.domain.enumeration.CardType.DEBIT;
import static com.valex.domain.validation.ValidateCardToCreateOrder.calculateTotalPrice;

import com.valex.domain.dto.CardDto;
import com.valex.domain.dto.OrderDto;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.mapper.OrderMapper;
import com.valex.domain.model.Order;
import com.valex.domain.model.Product;
import com.valex.domain.validation.ValidateCardToCreateOrder;
import com.valex.domain.vo.CreateOrderVo;
import com.valex.repository.OrderRepository;
import com.valex.repository.ProductRepository;
import com.valex.service.CardService;
import com.valex.service.OrderService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CardService cardService;

  @Autowired
  private OrderMapper orderMapper;

  @Transactional
  public OrderDto create (CreateOrderVo createOrderVo) {
    CardDto cardDto = cardService.findByIdOrFail(createOrderVo.getCard().getId());
    ValidateCardToCreateOrder.valid(cardDto, createOrderVo);

    createOrderVo.setDate(Calendar.getInstance().getTime());
    Order order = orderRepository.save(orderMapper.voToModel(createOrderVo));

    if (order.getPurchaseType() == DEBIT) {
      Long newBalance = cardDto.getBalance() - calculateTotalPrice(createOrderVo);
      cardService.updateBalance(cardDto.getId(), newBalance);
    }

    for (Product product: order.getProducts()) product.setOrder(order);
    List<Product> products = productRepository.saveAll(order.getProducts());

    order.setProducts(products);
    return orderMapper.modelToDto(order);
  }

  public void delete(Long id) {
    Optional<Order> optionalOrder = orderRepository.findById(id);

    if (optionalOrder.isEmpty()) {
      throw new NotFoundException("Order Not Found.");
    }

    Order order = optionalOrder.get();
    orderRepository.deleteById(order.getId());
  }

  public List<OrderDto> findOrderByPeriodAndCardId (Long cardId, Date startDate, Date finalDate) {
    List<Order> orders = orderRepository.findByCardIdAndByDateBetween(
        cardId,
        startDate,
        finalDate
    );
    return orderMapper.modelToDto(orders);
  }
}
