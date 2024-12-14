package servlet.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import servlet.DTO.Create.ItemCreateDTO;
import servlet.repository.*;
import servlet.service.IngredientService;
import servlet.service.ItemModifyIngredientService;
import servlet.service.ItemService;
import servlet.service.OrderService;
import servlet.singeleton.EntityManagerFactorySingeleton;
import servlet.util.JsonHelper;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    OrderService orderService = new OrderService();
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    HttpSession session = request.getSession();
    ItemService itemService = new ItemService(new IngredientService(),new ItemModifyIngredientService());
    OrderRepository orderRepository = new OrderRepository(em);
    var orderObjFromSession = session.getAttribute("orders");
    var orderJson = JsonHelper.toJson(orderObjFromSession);
    var pizzaRepository = new PizzaRepository();
    var ingredientRepository = new IngredientRepository(em);
    var pizzaSizeAndPrice = new PizzaSizeAndPricesRepository();

    var order = orderRepository.createOrder();
    var orderId = order.getId();
    Set<ItemCreateDTO> itemsDTO = itemService.createItems(orderJson, orderId);
    ItemRepository itemRepository = new ItemRepository(pizzaRepository,ingredientRepository,pizzaSizeAndPrice,em);
    var items = itemRepository.createItems(itemsDTO, order);

    var totalPriceOrder = orderService.getTotalPriceFromItems(items,0);
    orderRepository.updatePriceOrder(Math.toIntExact(orderId),totalPriceOrder);
    itemRepository.saveItems(items);

    request.setAttribute("idOrder",orderId);
    request.getSession().setAttribute("idOrder", String.valueOf(orderId));
    //RequestDispatcher dispatcher = request.getRequestDispatcher("/getPay");
    session.removeAttribute("orders");
    session.removeAttribute("bucket");

    RequestDispatcher dispatcher = request.getRequestDispatcher("/payForm");
    dispatcher.forward(request, response);
    System.out.println("aaa");

  }
}
