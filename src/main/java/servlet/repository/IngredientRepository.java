package servlet.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import servlet.model.Ingredient;
import servlet.model.Pizza;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.util.List;

public class IngredientRepository {
  private EntityManager em;

  public IngredientRepository(EntityManager em) {
    this.em = em;
  }

  public List<Ingredient> findAll() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Ingredient> cq = cb.createQuery(Ingredient.class);
    Root<Ingredient> root = cq.from(Ingredient.class);
    cq.select(root);
    TypedQuery<Ingredient> q = em.createQuery(cq);
    return q.getResultList();
  }

  public Ingredient findById(int id) {
    Ingredient ingredient = em.find(Ingredient.class, id);
    em.close();
    return ingredient;
  }

  public List<Ingredient> findIngredientsWhereIdPizza(int pizzaId) {
    List<Ingredient> ingredients = em.createQuery(
            "SELECT i from Ingredient i join i.pizzas p where p.id = :PizzasId", Ingredient.class)
        .setParameter("PizzasId", pizzaId)
        .getResultList();
    em.close();
    return ingredients;
  }

  public List<Ingredient> findOptionalIngredientsWhereIdPizza(int pizzaId) {
    List<Ingredient> ingredients = em.createQuery(
            "SELECT i from Ingredient i where i.id not in :PizzasId", Ingredient.class)
        .setParameter("PizzasId", pizzaId)
        .getResultList();
    em.close();
    return ingredients;
  }
}
