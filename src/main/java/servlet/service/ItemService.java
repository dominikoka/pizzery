package servlet.service;

import com.fasterxml.jackson.databind.JsonNode;
import servlet.DTO.Create.ItemCreateDTO;
import servlet.DTO.Read.ItemReadDTO;
import servlet.model.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ItemService {

  IngredientService ingredientService;
  ItemModifyIngredientService extraIngredientService;


  public ItemService(IngredientService ingredientService, ItemModifyIngredientService extraIngredientService) {
    this.ingredientService = ingredientService;
    this.extraIngredientService = extraIngredientService;
  }


  public Set<ItemCreateDTO> createItems(JsonNode jsonNode, Long orderId) {
    Set<ItemCreateDTO> res = new HashSet<>();
    for (var singleItemJson : jsonNode) {
      var itemDTO = createItem(singleItemJson, orderId);
      res.add(itemDTO);
    }
    return res;
  }

  private ItemCreateDTO createItem(JsonNode singleItemJson, Long orderId) {
    ItemCreateDTO item = new ItemCreateDTO();
    var id = singleItemJson.get("pizzaId").asLong();
    var sizePizza = singleItemJson.get("size").asInt();
    item.setPizzaId((int) id);
    item.setSizeAndPriceID(sizePizza);
    item.setOrderId(Math.toIntExact(orderId));
    var ingredients = createIngredients(singleItemJson.path("ingredients"));
    item.setModifyIngredientsIDs(ingredients);
    return item;
  }

  private List<Integer> createIngredients(JsonNode extraIngredients) {
    List<Integer> ingredientsList = new ArrayList<>();
    for (var ingredient : extraIngredients) {
      ingredientsList.add(ingredient.asInt());
    }
    return ingredientsList;
  }


  public Set<ItemReadDTO> mapItemsToDTO(Set<Item> items) {

    Set<ItemReadDTO> itemsDTO = new HashSet<>();
    for (var item : items) {
      itemsDTO.add(mapItemToDTO(item));
    }
    return itemsDTO;
  }

  private ItemReadDTO mapItemToDTO(Item item) {

    var pizzaName = getPizzaName(item);
    var pizzaSize = getPizzaSize(item);
    var pizzaPrice = getPizzaPrice(item);
    var ingredientsDTO = ingredientService.mapIngredientsToIngredientsDTO(item.getPizza().getIngredients());
    var extraIngredientsDTO = extraIngredientService.mapExtraIngredientsToDTO(item.getItemModifyIngredients());
    return new ItemReadDTO(pizzaName, pizzaSize, pizzaPrice, ingredientsDTO, extraIngredientsDTO);
  }

  private String getPizzaName(Item item) {
    return item.getPizza().getName();
  }

  private String getPizzaSize(Item item) {
    return item.getSizeAndPrices().getSize().toString();
  }

  private double getPizzaPrice(Item item) {
    return item.getSizeAndPrices().getPrice();
  }



}
