package servlet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "pizzas")
@Data
public class Pizza {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  private String description;
  @Column(name = "img_url")
  private String img_url;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "pizza_ingredient",
      joinColumns = @JoinColumn(name = "PizzasId"),
      inverseJoinColumns = @JoinColumn(name = "IngredientsId")
  )
  private Set<Ingredient> ingredients = new HashSet<>();
  @OneToMany(mappedBy = "pizzaSizeAndPrices", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<PizzaSizeAndPrices> sizeAndPrices = new HashSet<>();

  @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Item> item = new HashSet<>();

  public Pizza(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Pizza() {
  }

  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
    ingredient.getPizzas().add(this);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pizza pizza = (Pizza) o;
    return id == pizza.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
