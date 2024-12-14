package servlet.model;

import lombok.Data;

@Data
public class IngredientSelection {
  public IngredientSelection(boolean selected, Boolean isFree, Ingredient ingredient) {
    this.selected = selected;
    this.isFree = isFree;
    this.ingredient = ingredient;
  }

  private Ingredient ingredient;
  private Boolean isFree;
  private boolean selected;



}
