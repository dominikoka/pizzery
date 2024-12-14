package servlet.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import servlet.service.IngredientSelectionService;
import servlet.service.PizzaService;
import servlet.util.HashMapSort;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/loadPizza")
public class PizzaServlet extends HttpServlet {
  @SneakyThrows
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    var idPizza = Integer.parseInt(request.getParameter("idPizza"));
    System.out.println(idPizza);
    Gson gson = new Gson();
    PizzaService pizzaService = new PizzaService();
    var pizza = pizzaService.getPizzaById(idPizza);
    IngredientSelectionService ingredientSelectionService = new IngredientSelectionService();
    var ingredients = ingredientSelectionService.selectedIngredients(pizza);
    var sizeAndPrice = pizzaService.getIdAndPrice(pizza);
    HashMap<Integer, Double> sizeAndPrizeSorted = HashMapSort.sortByValue(sizeAndPrice);

    List<Double> oneRecordSortedList = new ArrayList<>();
    List<List<Double>> sortedList = new ArrayList<>();
    for(var key : sizeAndPrizeSorted.entrySet()) {
      oneRecordSortedList.add(key.getKey().doubleValue());
      oneRecordSortedList.add(key.getValue().doubleValue());
      sortedList.add(oneRecordSortedList);
      oneRecordSortedList = new ArrayList<>();

    }

    sortedList.forEach(System.out::println);


    String priceJson = gson.toJson(sortedList);


    request.setAttribute("price", priceJson);
    System.out.println("po wczytaniu ing");
    request.setAttribute("ingredients", ingredients);
    request.setAttribute("pizza", pizza);
    request.getRequestDispatcher("/elements/pizza.jsp").forward(request, response);
  }
}
