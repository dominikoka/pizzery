package servlet.DTO.Read;

import lombok.Data;
import lombok.EqualsAndHashCode;
import servlet.model.ItemModifyIngredient;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExtraIngredientReadDTO extends IngredientReadDTO{
  private String action;


  public ExtraIngredientReadDTO(String name, double price, ItemModifyIngredient.Action action) {
    super(name, price);
    this.action = String.valueOf(action);
  }
}
