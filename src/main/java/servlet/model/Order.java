package servlet.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="orders")
@Data
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name="is_paid")
  private Boolean is_paid=false;
  @Column(name="total_price")
  private Double total_price;

  @Column(name= "date", nullable = false, updatable = false)
  //@Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private LocalDateTime date;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Item> items = new HashSet<>();


}
