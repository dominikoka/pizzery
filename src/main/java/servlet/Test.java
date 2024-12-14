package servlet;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Transaction;
//import servlet.model.Ingredient;
import servlet.model.Pizza;
import servlet.util.HibernateUtil;

import java.io.IOException;

@WebServlet("/test")
public class Test extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    System.out.println("testowa klasam");
//
////    Ingredient onion = new Ingredient("kiełbasa", "kiełbasa", 4.50, "img/ingredients/kielbasa.jpg");
//    Pizza onion = new Pizza("pepperoni","Pizza pepperoni to popularna odmiana pizzy z kuchni amerykańskiej, która charakteryzuje się dodatkiem pikantnej kiełbasy pepperoni. Na cienkim cieście rozprowadzony jest sos pomidorowy, następnie posypana serem mozzarella i obficie udekorowana plasterkami pepperoni.", Pizza.Size.LARGE,48.0);
//    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//    Session session = sessionFactory.openSession();
//    Transaction transaction = null;
//
//    try {
//      transaction = session.beginTransaction();
//      session.save(onion);
//      transaction.commit();
//      System.out.println("dodales item");
//    } catch (Exception e) {
//      System.out.println("nie dodales item :(((((");
//      e.printStackTrace();
//    }
  }
}
