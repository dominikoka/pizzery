package servlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import servlet.repository.OrderRepository;
import servlet.service.OrderService;
import servlet.singeleton.EntityManagerFactorySingeleton;

@WebServlet("/ordersJSON")
public class OrdersJSONServlet extends HttpServlet {
  private OrderRepository orderRepository;
  private OrderService orderService;
  private ObjectMapper objectMapper;

  @Override
  public void init() throws ServletException {

    orderService = new OrderService();
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
  }

  @SneakyThrows
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    orderRepository = new OrderRepository(em);
    response.setHeader("Access-Control-Allow-Origin", "*");
    var orders = orderRepository.getOrders();
    var ordersDTO = orderService.mapOrdersToDTO(orders);
    String ordersJson = objectMapper.writeValueAsString(ordersDTO);
    System.out.println("orders servlet");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(ordersJson);

  }
}
