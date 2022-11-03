package com.valex.repository;


import com.valex.domain.model.Order;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository <Order, Long> {

  @Query (value = "SELECT o FROM orders o WHERE o.card.id = :card_id AND date BETWEEN :start AND :end")
  List<Order> findByCardIdAndByDateBetween (
      @Param("card_id") Long cardId,
      @Param("start") Date start,
      @Param("end") Date end
  );
}
