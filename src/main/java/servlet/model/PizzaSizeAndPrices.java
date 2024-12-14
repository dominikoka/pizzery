package servlet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pizzassizeandprices")
@Data
public class PizzaSizeAndPrices {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "size", nullable = false)
  @Enumerated(EnumType.STRING)
  private Size size;
  @Column(name = "price", nullable = false)
  private Double price;
  @ManyToOne
  @JoinColumn(name = "pizza_id_fk", nullable = false)
  private Pizza pizzaSizeAndPrices;
  @OneToMany(mappedBy = "sizeAndPrices", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Item> item = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PizzaSizeAndPrices pizzaSizeAndPrices = (PizzaSizeAndPrices) o;
    return id == pizzaSizeAndPrices.id;
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public String toString() {
    return String.valueOf(id);
  }

  public enum Size {
    SMALL, MEDIUM, LARGE
  }


}
