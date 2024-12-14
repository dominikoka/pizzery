package servlet.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlet.model.Ingredient;
import servlet.model.Pizza;
import servlet.repository.IngredientRepository;
import servlet.service.PizzaService;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createItem")
public class CreateItemServlet extends HttpServlet {
  JsonObject result = new JsonObject();
  JsonArray items = new JsonArray();
  int orderCounter = 1;
  JsonObject resultJson = new JsonObject();
  JsonArray itemsJson = new JsonArray();
  JsonObject objJson = new JsonObject();

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    String orderId = "order-" + orderCounter;
    StringBuilder jsonBuilder = new StringBuilder();
    try (BufferedReader reader = request.getReader()) {
      String line;
      while ((line = reader.readLine()) != null) {
        jsonBuilder.append(line);
      }
    }
    Gson gson = new Gson();
    JsonObject order = gson.fromJson(jsonBuilder.toString(), JsonObject.class);
    items.add(order);
    HttpSession session = request.getSession();

    result.add("orders", (JsonElement) items);
    session.setAttribute("orders", result);


//TODO poprawic
    JsonObject resultJson = new JsonObject();
    JsonArray itemsJson = new JsonArray();
    JsonObject objJson = new JsonObject();
    var json = session.getAttribute("orders");
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(json.toString());
    var orders = jsonNode.path("orders");
    PizzaService servicePizza = new PizzaService();
    IngredientRepository ingredientRepository = new IngredientRepository(em);
    List<Ingredient> ingredients = ingredientRepository.findAll();
    List<List<Ingredient>> extraIngredientsList = new ArrayList<>();
    List<Pizza> pizzas = new ArrayList<>();
    String orderString = "";

    for (var item : orders) {
      double pizzaPrice = 0;
      objJson = new JsonObject();

      var id = Integer.valueOf(item.path("pizzaId").asText());
      Integer sizePizza = Integer.valueOf(item.path("size").asText());
//      var type = order.path("type").asText();
//      var originalIngredients = order.path("originalIngredients").asText();

      var pizza = servicePizza.getPizzaById(id);
      objJson.addProperty("name", pizza.getName());
      objJson.addProperty("desc", pizza.getDescription());

      for (var sizeAndPrices : pizza.getSizeAndPrices()) {
        if (sizePizza == sizeAndPrices.getId()) {
          objJson.addProperty("size", sizeAndPrices.getSize().toString());
//          objJson.addProperty("price", sizeAndPrices.getPrice().toString());
          pizzaPrice +=sizeAndPrices.getPrice();
        }
      }
      List<Integer> indexIngredientsToRemove = new ArrayList<>();

      var extraIngredients = item.path("ingredients");
      for (var extraIngredient : extraIngredients) {
        Integer extraIngredientInt = Integer.parseInt(String.valueOf(extraIngredient));
        if (extraIngredientInt < 0) {
          indexIngredientsToRemove.add(extraIngredientInt * -1);
        }
      }
      JsonArray itemsDefault = new JsonArray();
      Integer counterIngredientDefault = 1;
      for (var ingredient : pizza.getIngredients()) {
        Boolean toRemoveDefault = true;
        for (var indexToRemove : indexIngredientsToRemove) {
          if (ingredient.getId() == indexToRemove) {
            toRemoveDefault = false;
          }
        }
        if (toRemoveDefault) {
          itemsDefault.add(ingredient.getName());
//          objJson.addProperty("ingredient" + counterIngredientDefault, ingredient.getName());
          counterIngredientDefault += 1;
        }
      }
      objJson.add("defaultIngredients",itemsDefault);

      var extraIngredientsCounter = 1;
      JsonArray extraItems = new JsonArray();
      for (var ingredient : extraIngredients) {
        Integer index = Integer.parseInt(String.valueOf(ingredient));
        for (var oneIngredient : ingredients) {
          if (oneIngredient.getId() == index && index > 0) {

            extraItems.add(oneIngredient.getName());
            pizzaPrice+=oneIngredient.getPrice();
            extraIngredientsCounter += 1;
          }
        }
      }
      objJson.add("extraIngredients",extraItems);
      counterIngredientDefault = 0;
      extraIngredientsCounter = 0;
      objJson.addProperty("price", pizzaPrice);
      itemsJson.add(objJson);
    }
    resultJson.add("orders", (JsonElement) itemsJson);

    session.setAttribute("bucket", resultJson);



    orderCounter++;
  }
}
