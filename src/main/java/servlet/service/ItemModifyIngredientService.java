package servlet.service;

import servlet.DTO.Read.ExtraIngredientReadDTO;
import servlet.model.Ingredient;
import servlet.model.ItemModifyIngredient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemModifyIngredientService {
  public static java.util.Set<ItemModifyIngredient> getModifyIngredientsFromListById(List<Ingredient> ingredients,
                                                                                     List<Integer> modifyIngredients) {
    Set<ItemModifyIngredient> itemsModifyIngredient = new HashSet<ItemModifyIngredient>();
    for (Integer i : modifyIngredients) {
      ItemModifyIngredient item = new ItemModifyIngredient();
      if (i > 0) {
        item = itemModifyIngredientADD(ingredients.stream().filter(in -> in.getId() == i).findAny().orElse(null));
      } else {
        item =
            itemModifyIngredientREMOVE(ingredients.stream().filter(in -> in.getId() == i * -1).findAny().orElse(null));
      }
      itemsModifyIngredient.add(item);
    }
    return itemsModifyIngredient;
  }

  private static ItemModifyIngredient itemModifyIngredientREMOVE(Ingredient ingredient) {
    ItemModifyIngredient itemModifyIngredient = new ItemModifyIngredient();
    itemModifyIngredient.setIngredient(ingredient);
    itemModifyIngredient.setAction(ItemModifyIngredient.Action.REMOVE);
    return itemModifyIngredient;
  }

  public static ItemModifyIngredient itemModifyIngredientADD(Ingredient ingredient) {
    ItemModifyIngredient itemModifyIngredient = new ItemModifyIngredient();
    itemModifyIngredient.setIngredient(ingredient);
    itemModifyIngredient.setAction(ItemModifyIngredient.Action.ADD);
    return itemModifyIngredient;
  }

  public Set<ExtraIngredientReadDTO> mapExtraIngredientsToDTO(Set<ItemModifyIngredient> extraIngredients) {
    Set<ExtraIngredientReadDTO> extraIngredientReadDTOs = new HashSet<>();
    for (ItemModifyIngredient itemModifyIngredient : extraIngredients) {
      extraIngredientReadDTOs.add(mapExtraIngredientToDTO(itemModifyIngredient));
    }
    return extraIngredientReadDTOs;
  }

  private ExtraIngredientReadDTO mapExtraIngredientToDTO(ItemModifyIngredient itemModifyIngredient) {
    return new ExtraIngredientReadDTO(itemModifyIngredient.getIngredient().getName(),
        itemModifyIngredient.getIngredient().getPrice(), itemModifyIngredient.getAction());
  }
}
