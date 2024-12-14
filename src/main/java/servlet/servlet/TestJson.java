package servlet.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.PrintWriter;

@WebServlet("/json")
public class TestJson extends HttpServlet {
  @SneakyThrows
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    PrintWriter out = resp.getWriter();
    HttpSession session = req.getSession();
    var json = session.getAttribute("orders");
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(json.toString());
    var orders = jsonNode.path("orders");
    for (var order : orders) {
      var id = order.path("pizzaId").asText();
      var type = order.path("type").asText();
      var originalIngredients = order.path("originalIngredients").asText();
      out.print(id);
      out.print(" ");
      out.print(type);
      out.print(" ");
      out.print(originalIngredients);
      var ingredients = order.path("ingredients");
      for (var ingredient : ingredients) {
        out.print(ingredient.asText());
      }
    }
    out.print(json);
  }
}
