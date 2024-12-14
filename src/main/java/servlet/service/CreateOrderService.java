package servlet.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class CreateOrderService {

  @SneakyThrows
  public void createItems(Object orders) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(orders.toString());
    var ordersJson = jsonNode.path("orders");
    for (var item : ordersJson) {
      var id = Integer.valueOf(item.path("pizzaId").asText());
      Integer sizePizza = Integer.valueOf(item.path("size").asText());
    }
  }
}
