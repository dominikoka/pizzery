package servlet.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import servlet.repository.OrderRepository;
import servlet.service.OrderService;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/payForm")
public class PayFormServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    String idOrder="";
    if (session.getAttribute("idOrder") != null) {
      System.out.println(session.getAttribute("idOrder"));
      idOrder = (String) session.getAttribute("idOrder");
    } else {

      StringBuilder sb = new StringBuilder();
      BufferedReader br = request.getReader();
      String str = null;
      while ((str = br.readLine()) != null) {
        sb.append(str);
        //System.out.println(str);
      }
      JSONObject jObj = new JSONObject(sb.toString());
      idOrder = jObj.getString("id");
    }
    request.getSession().setAttribute("idPayOrder", idOrder);
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OrderRepository orderRepository = new OrderRepository(em);


    OrderService orderService = new OrderService();
    var priceOrder = orderRepository.getPriceIdOrder(Integer.parseInt(idOrder));


    String paymentFormUrl = "/elements/payForm.jsp";
    System.out.println("aaa");
//    request.getRequestDispatcher("/elements/payForm.jsp").forward(request, response);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    System.out.println("URL do przekierowania: " + paymentFormUrl);
    String jsonResponse = String.format(
        Locale.US,
        "{\"paymentUrl\":\"%s\", \"amount\":%.2f}",
        paymentFormUrl, priceOrder
    );
    request.getSession().setAttribute("amount", priceOrder);
    System.out.println(priceOrder);
    System.out.println("Generowany JSON: " + jsonResponse);
    response.getWriter().write(jsonResponse);
  }
}
