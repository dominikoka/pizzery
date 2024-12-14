package servlet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class JsonHelper {
  public static JsonNode toJson(Object obj) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = null;
    try {
      jsonNode = mapper.readTree(obj.toString());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return jsonNode.path("orders");
  }
}
