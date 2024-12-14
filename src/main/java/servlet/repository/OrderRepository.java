package servlet.repository;

import jakarta.persistence.EntityManager;
import servlet.model.Order;

import java.util.List;

public class OrderRepository {

  private EntityManager em;

  public OrderRepository(EntityManager em) {
    this.em = em;
  }

  public List<Order> getOrders() {
    List<Order> orders = em.createQuery(
            "SELECT DISTINCT o FROM Order o " +
                "LEFT JOIN FETCH o.items i " +
                "LEFT JOIN FETCH i.sizeAndPrices " +
                "LEFT JOIN FETCH i.itemModifyIngredients imi " +
                "LEFT JOIN FETCH imi.ingredient " +
                "LEFT JOIN FETCH i.pizza p " +
                "LEFT JOIN FETCH i.pizza.ingredients", Order.class)
        .getResultList();
    em.close();
    return orders;
  }

  public double getPriceIdOrder(int orderId) {
    double price = em.createQuery(
            "select  o.total_price From Order o where o.id = :orderId", Double.class
        ).setParameter("orderId", orderId).
        getSingleResult();
    return price;
  }

  public Order createOrder() {
    Order order = new Order();
    try {
      em.getTransaction().begin();

      em.persist(order);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
    }
    return order;
  }
  public void updatePriceOrder(int orderId, double price) {
    Order order = em.find(Order.class, orderId);
    order.setTotal_price(price);
    em.getTransaction().begin();
    em.merge(order);
    em.getTransaction().commit();
  }

}
