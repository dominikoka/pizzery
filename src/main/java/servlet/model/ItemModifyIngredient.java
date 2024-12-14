package servlet.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "itemmodifyingredient")
@Data
public class ItemModifyIngredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "items_id", nullable = false)
  private Item item;

  @ManyToOne
  @JoinColumn(name = "ingredient_id")
  private Ingredient ingredient;

  @Column(name = "action", nullable = false)
  @Enumerated(EnumType.STRING)
  private ItemModifyIngredient.Action action;

  public enum Action {
    REMOVE, ADD
  }

}
