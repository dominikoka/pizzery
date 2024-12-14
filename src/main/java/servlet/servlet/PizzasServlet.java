package servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.service.PizzaService;

import java.io.IOException;

@WebServlet("/loadPizzas")
public class PizzasServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    PizzaService pizzaService = new PizzaService();
    var pizzas = pizzaService.getAllPizzas();



    req.setAttribute("pizzas", pizzas);

    req.getRequestDispatcher("/elements/pizzas.jsp").forward(req, resp);
  }
}
