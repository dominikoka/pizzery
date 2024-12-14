package servlet.service;

import servlet.DTO.Read.IngredientReadDTO;
import servlet.model.Ingredient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IngredientService {
  public static List<Ingredient> getModifyFromListById(List<Ingredient> ingredients, List<Integer> modifyIngredients) {
    var modifyIngredientsList = new ArrayList<Ingredient>();
    for (var ingredient : ingredients) {
      if (modifyIngredients.contains(ingredient.getId())) {
        modifyIngredientsList.add(ingredient);
      }
    }
    return modifyIngredientsList;
  }

  public Set<IngredientReadDTO> mapIngredientsToIngredientsDTO(Set<Ingredient> ingredients) {
    Set<IngredientReadDTO> ingredientsDTO = new HashSet<>();
    for(var ingredient: ingredients) {
      ingredientsDTO.add(mapIngredientToIngredientDTO(ingredient));
    }
    return ingredientsDTO;
  }

  private IngredientReadDTO mapIngredientToIngredientDTO(Ingredient ingredient) {
   return new IngredientReadDTO(ingredient.getName(),0);
  }
}
