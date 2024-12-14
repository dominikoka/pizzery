package servlet.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import servlet.model.Ingredient;
import servlet.model.Pizza;
import servlet.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

public class BucketService {


  @SneakyThrows
  public void setSessionBucket() {
    Gson gson = new Gson();



  }
}
