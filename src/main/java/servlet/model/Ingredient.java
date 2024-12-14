package servlet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@Data
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  @Column(name = "name", nullable = false, columnDefinition = "TEXT")
  private String name;
  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  private String description;
  @Column(name = "price", nullable = false)
  private double price;
  @Column(name = "image_URL", nullable = false, length = 100)
  private String image_url;

  @ManyToMany(mappedBy = "ingredients")
  private Set<Pizza> pizzas = new HashSet<>();
  //  @ManyToOne
//  @JoinColumn(name = "itemModifyIngredient_id", nullable = false)
//  private ItemModifyIngredient itemModifyIngredient;
  @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<ItemModifyIngredient> itemModifyIngredients = new HashSet<>();

  public Ingredient(String name, String description, double price, String image_url) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.image_url = image_url;
  }


  public Ingredient() {

  }

  public void addPizza(Pizza pizza) {
    pizzas.add(pizza);
    pizza.getIngredients().add(this);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ingredient pizza = (Ingredient) o;
    return id == pizza.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
