package servlet.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.json.JSONObject;
import servlet.repository.OrderRepository;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/getRabat")
public class GetRabatServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession();
    int idOrder = Integer.parseInt((String) session.getAttribute("idPayOrder"));
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    var orderRepository = new OrderRepository(em);
    Integer result = 0;
    double price=0;
    double defaultPrice=0;
    if(price==0)
    {
      price = orderRepository.getPriceIdOrder(idOrder);
      defaultPrice = price;
    }
    StringBuilder sb = new StringBuilder();
    BufferedReader br = request.getReader();
    String str = null;
    while ((str = br.readLine()) != null) {
      sb.append(str);
      //System.out.println(str);
    }
    JSONObject jObj = new JSONObject(sb.toString());
    if(jObj.getString("rabat").equals("123")){
      price=price*0.8;
    }
    else
    {
      price = defaultPrice;
    }
    session.setAttribute("defaultPrice", defaultPrice);
    session.setAttribute("newPrice", price);

    System.out.println(price);
    JSONObject responseJson = new JSONObject();
    responseJson.put("result", price);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print(responseJson.toString());
  }
}
