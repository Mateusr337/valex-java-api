package com.valex.domain.model;

import com.valex.domain.enumeration.CardType;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "orders")
@Entity (name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(
      name = "card_id",
      nullable = false
  )
  private Card card;

  @Column (nullable = false)
  @Enumerated (EnumType.STRING)
  private CardType purchaseType;

  @Column (nullable = false)
  private String shopName;

  @OneToMany (
      mappedBy = "order",
      cascade = { CascadeType.DETACH },
      fetch = FetchType.LAZY
  )
  @Column (name = "products")
  private List<Product> products;

  @Column (nullable = false)
  private Date date;

}
