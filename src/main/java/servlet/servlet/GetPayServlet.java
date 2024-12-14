package servlet.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import servlet.DTO.Payu.BuyerPayu;
import servlet.DTO.Payu.OrderPayu;
import servlet.DTO.Payu.ProductPayu;
import servlet.repository.OrderRepository;
import servlet.service.EmailSender;
import servlet.service.PayuService;
import servlet.service.SmsSender;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

@WebServlet("/getPay")
public class GetPayServlet extends HttpServlet {
  private ObjectMapper objectMapper;
  @Override
  public void init() throws ServletException {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OrderRepository orderRepository = new OrderRepository(em);
    HttpSession session = request.getSession();
    checkPrice(session, orderRepository);
    StringBuilder sb = new StringBuilder();
    BufferedReader br = request.getReader();
    String str = null;
    while ((str = br.readLine()) != null) {
      sb.append(str);
      //System.out.println(str);
    }
    JSONObject jObj = new JSONObject(sb.toString());
    //HttpSession session = request.getSession();
    String idOrder = (String) session.getAttribute("idPayOrder");

    var name = jObj.getString("name");
    var adress = jObj.getString("adress");
    var phone = jObj.getString("phone");
    var lname = jObj.getString("lname");
    var email = jObj.getString("email");
//    EmailSender emailSender = new EmailSender("", "");
//    emailSender.sendEmail(email,"pizzeria", "zamowienie zostanie dostarczone na adress" + adress);
//    SmsSender smsSender = new SmsSender(Integer.parseInt(phone));
//    smsSender.sendSms(Integer.parseInt(phone),"zamowienie zostalo wyslane")

//    var idOrder = request.getParameter("id")!=null ? Integer.parseInt(request.getParameter("id") ) : (long)request
//    .getAttribute("idOrder");
    //var idOrder = 79;
    var priceOrder = orderRepository.getPriceIdOrder(Integer.parseInt(idOrder));

    JsonNode token = null;
    try {
      token = PayuService.getToken();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    var buyer = new BuyerPayu();
    buyer.setEmail(email);
    buyer.setFirstName(name);
    buyer.setLastName(lname);
    buyer.setLanguage("PL");
    buyer.setPhone(phone);
    //buyer.setAddress(adress);

    var product = new ProductPayu();
    product.setName("pizza");
    product.setQuantity(2);
    product.setUnitPrice((int) priceOrder);

    Random rd = new Random();
    int idPay = rd.nextInt(100);
    var order =
        new OrderPayu.Builder(product.getUnitPrice(), String.valueOf(idOrder))
            .SetExtOrderId(String.valueOf(idPay))
            .SetBuyer(buyer)
            .SetCustomerIp("127.0.0.1")
            .SetDescription("desc")
            .SetMerchantPosId(String.valueOf(idOrder))
            .SetCurrencyCode("PLN")
            .SetProducts(product)
            .SetNotifyUrl("https://your.eshop.com/notify")
            .build();
    PayuService payuService = new PayuService();
    var payLink = payuService.CreateOrder(order, token);
    System.out.println(payLink);
    //response.getWriter().write(payLink);
    //response.sendRedirect(payLink);
    try {
      String orderJson = objectMapper.writeValueAsString(payLink);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    System.out.println(payLink);
    try {
      response.getWriter().write(payLink);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void checkPrice(HttpSession session, OrderRepository orderRepository) {

    double defaultPrice = (double) session.getAttribute("defaultPrice");
    double newPrice = (double) session.getAttribute("newPrice");
    if (newPrice != defaultPrice) {
      orderRepository.updatePriceOrder(Integer.parseInt((String) session.getAttribute("idPayOrder")),newPrice);
    }
  }
}
