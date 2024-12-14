package servlet.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import servlet.model.PizzaSizeAndPrices;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.util.List;

public class PizzaSizeAndPricesRepository {
  private EntityManager em;

  public PizzaSizeAndPricesRepository() {
    this.em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
  }

  public List<PizzaSizeAndPrices> findAll() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<PizzaSizeAndPrices> cq = cb.createQuery(PizzaSizeAndPrices.class);
    Root<PizzaSizeAndPrices> root = cq.from(PizzaSizeAndPrices.class);
    cq.select(root);
    TypedQuery<PizzaSizeAndPrices> q = em.createQuery(cq);
    return q.getResultList();
  }
}


