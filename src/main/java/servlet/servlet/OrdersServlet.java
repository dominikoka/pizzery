package servlet.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {

  public void init() {

  }


  @SneakyThrows
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    request.getRequestDispatcher("/elements/orders.jsp").forward(request, response);
  }

  public void destroy() {
  }
}