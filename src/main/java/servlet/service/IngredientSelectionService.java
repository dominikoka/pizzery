package servlet.service;

import jakarta.persistence.EntityManager;
import servlet.model.Ingredient;
import servlet.model.IngredientSelection;
import servlet.model.Pizza;
import servlet.repository.IngredientRepository;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.util.ArrayList;
import java.util.List;

public class IngredientSelectionService {

  public List<IngredientSelection> selectedIngredients(Pizza pizza) {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();//wczytuje singeleton
    List<IngredientSelection> ingredientsPizza = new ArrayList<IngredientSelection>(); //tworzy liste skladnikow
    IngredientRepository ingredientRepository = new IngredientRepository(em); //wczytuje ingredient repository
    List<Integer> idSelectedIngredients = new ArrayList<>();
    for (Ingredient ingredient : pizza.getIngredients()) {
      ingredientsPizza.add(new IngredientSelection(true, true, ingredient));
      idSelectedIngredients.add(ingredient.getId());
    }
    for (Ingredient ingredient : ingredientRepository.findAll())
      if (!idSelectedIngredients.contains(ingredient.getId())) {
        ingredientsPizza.add(new IngredientSelection(false, false, ingredient));
      }
    return ingredientsPizza;
  }
}
