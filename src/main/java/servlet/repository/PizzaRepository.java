package servlet.repository;

import jakarta.persistence.*;
import servlet.model.Ingredient;
import servlet.model.Pizza;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.util.List;

public class PizzaRepository {

  private EntityManagerFactory emf;

  @PersistenceContext
  //private EntityManager em;

//  public PizzaRepository() {
//    this.emf = Persistence.createEntityManagerFactory("thePersistenceUnit");
//  }

  public List<Pizza> findAll() {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    List<Pizza> pizzas =
        em.createQuery("SELECT p FROM Pizza p LEFT JOIN FETCH p.ingredients", Pizza.class).getResultList();
    em.close();
    return pizzas;
  }


  public Pizza findById(int id) {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    Pizza pizza = null;
    try {
      TypedQuery<Pizza> query = em.createQuery("select p from Pizza p left join fetch p.ingredients left join fetch p.sizeAndPrices where p.id = :id"
          , Pizza.class);
      query.setParameter("id", id);
      pizza = query.getSingleResult();
    } finally {
      em.close();
    }
    return pizza;
  }

  public Pizza findByIdWithSize(int id) {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    Pizza pizza = null;
    try {
      TypedQuery<Pizza> query = em.createQuery("select p from Pizza p left join fetch p.sizeAndPrices where p.id = :id"
          , Pizza.class);
      query.setParameter("id", id);
      pizza = query.getSingleResult();
    } finally {
      em.close();
    }
    return pizza;
  }

  public List<Pizza> findByIds(List<Integer> ids) {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    List<Pizza> pizza = null;
    try {
      TypedQuery<Pizza> query = em.createQuery("select p from Pizza p left join fetch p.ingredients where p.id in (:id)"
          , Pizza.class);
      query.setParameter("id", ids);
      pizza = query.getResultList();
    } finally {
      em.close();
    }
    return pizza;
  }

  //@Transactional
  public void addPizzaIngredient(int pizzaId, int ingredientId) {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      Pizza pizza = em.find(Pizza.class, pizzaId);
      Ingredient ingredient = em.find(Ingredient.class, ingredientId);
      pizza.addIngredient(ingredient);
      em.merge(pizza);
      tx.commit();
    } catch (Exception e) {
      if (tx.isActive()) tx.rollback();
      throw e;
    } finally {
      em.close();
    }
  }
}
