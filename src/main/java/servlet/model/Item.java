package servlet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="items")
@Data
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  public Item()
  {
    this.itemModifyIngredients = new HashSet<>();
  }
//  @OneToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name="pizza_id",referencedColumnName = "id")
//  private Pizza pizza;



  @ManyToOne
  @JoinColumn(name = "pizza_id", nullable = false)
  private Pizza pizza;

  @ManyToOne
  @JoinColumn(name = "sizeandprices_id", nullable = false)
  private PizzaSizeAndPrices sizeAndPrices;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<ItemModifyIngredient> itemModifyIngredients;
  public void addItemModifyingIngredient(ItemModifyIngredient ingredient) {
    ingredient.setItem(this);
    itemModifyIngredients.add(ingredient);
  }

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;


  @Override
  public String toString() {
    return "Item{" + "id=" + id + ", pizzaId=" + pizza.getId() + ", sizeAndPrices= " + sizeAndPrices.getId() + "}" ;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item pizza = (Item) o;
    return id == pizza.id;
  }

  @Override
  public int hashCode() {
    return (id!=null)?Math.toIntExact(id):0;
  }


}
