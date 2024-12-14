package servlet.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import servlet.model.Pizza;
import servlet.model.PizzaSizeAndPrices;
import servlet.repository.IngredientRepository;
import servlet.repository.PizzaRepository;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class PizzaService {
  EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
  private PizzaRepository pr = new PizzaRepository();
  private IngredientRepository in = new IngredientRepository(em);

  public static Pizza getPizzaFromListById(List<Pizza> pizzas, int pizzaId) {
    for (Pizza p : pizzas) {
      if (p.getId() == pizzaId) {
        return p;
      }
    }
    throw new NoSuchElementException("Pizza with id " + pizzaId + " not found");
  }

  public List<Pizza> getAllPizzas() {

    return new ArrayList<Pizza>(pr.findAll());
  }

  @Transactional
  public void addPizzaIngredients(int idPizza, int idIngredient) {
    PizzaRepository pr = new PizzaRepository();
    pr.addPizzaIngredient(idPizza, idIngredient);
  }

  public Pizza getPizzaById(int idPizza) {
    return pr.findById(idPizza);
  }

  public Pizza getPizzaByIdSize(int idPizza) {
    return pr.findByIdWithSize(idPizza);
  }

  public HashMap<Integer, Double> getIdAndPrice(Pizza pizza) {
    HashMap<Integer, Double> idAndPrice = new HashMap<Integer, Double>();
    for (PizzaSizeAndPrices pizzaSizeAndPrices : pizza.getSizeAndPrices()) {
      idAndPrice.put(pizzaSizeAndPrices.getId(), pizzaSizeAndPrices.getPrice());
    }
    return idAndPrice;
  }
}
