package servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientManager {
  private static IngredientManager instance;
  private List<String> ingredients;

  private IngredientManager() {
    ingredients = Arrays.asList("blue cheese",
        "ham",
        "mushrooms",
        "bell peppers",
        "pineapple",
        "bacon",
        "arugula",
        "tuna",
        "garlic",
        "corn",
        "artichokes",
        "chili");
  }
  private static final List<String> ingredientsPepperoni = Arrays.asList(
      "cheese",
      "tomato",
      "basil",
      "salami",
      "olives",
      "cucumber",
      "onion",
      "pepper"
  );
  private static final List<String> ingredientsMargheritta = Arrays.asList(
      "cheese",
      "salami",
      "cucumber"
  );
  public static IngredientManager getInstance() {
    if (instance == null) {
      instance = new IngredientManager();
    }
    return instance;
  }
  public List<String> getIngredients() {
    return ingredients;
  }

}
