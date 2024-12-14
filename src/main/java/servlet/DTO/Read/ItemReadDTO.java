package servlet.DTO.Read;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ItemReadDTO {


  private String pizzaName;
  private String size;
  private double price;
  private double totalPrice;
  Set<IngredientReadDTO> defaultIngredients = new HashSet<>();
  Set<ExtraIngredientReadDTO> modifyIngredients = new HashSet<>();

  public ItemReadDTO(String pizzaName, String size, double price, Set<IngredientReadDTO> defaultIngredients,
                     Set<ExtraIngredientReadDTO> modifyIngredients) {
    this.pizzaName = pizzaName;
    this.size = size;
    this.price = price;
    this.defaultIngredients = defaultIngredients;
    this.modifyIngredients = modifyIngredients;
    totalPrice = price + modifyIngredientsPrice();
  }

  public double getTotalPrice() {
    return price + modifyIngredientsPrice();
  }

  private double modifyIngredientsPrice() {
    double totalPrice = 0;
    for (var ingredient : modifyIngredients) {
      totalPrice += ingredient.getPrice();
    }
    return totalPrice;
  }

}
