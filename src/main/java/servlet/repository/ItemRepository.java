package servlet.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import servlet.DTO.Create.ItemCreateDTO;
import servlet.model.*;
import servlet.service.ItemModifyIngredientService;
import servlet.service.PizzaService;
import servlet.service.SizeAndPriceService;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemRepository {
  final PizzaRepository pizzaRepository;
  final IngredientRepository ingredientRepository;
  final PizzaSizeAndPricesRepository priceRepository;

  private EntityManager em;
  public ItemRepository(PizzaRepository pr, IngredientRepository i, PizzaSizeAndPricesRepository p, EntityManager em) {
    pizzaRepository = pr;
    ingredientRepository = i;
    priceRepository = p;
    this.em = em;
  }

  public List<Item> createItems(Set<ItemCreateDTO> itemsDTO, Order order) {
    //TODO laduje wszystkie pizzas. mam metode ktora wczytuje tablice pizz.
    //TODO tez to nie operuje na bazie danych wiec raczej powinno byc w service
    //musi byc list bo czasem itemy sie powtarzaja
    List<Item> items = new ArrayList<>();
    var sizeAndPrices = loadData().sizeAndPrices;
    var pizzas = loadData().pizzas;
    var ingredients = loadData().ingredients;
    for (ItemCreateDTO itemDTO : itemsDTO) {
      var item = createItem(itemDTO, pizzas, ingredients, sizeAndPrices);
      item.setOrder(order);

      items.add(item);
    }
    return items;
  }

  private Item createItem(ItemCreateDTO itemDTO, List<Pizza> pizzas, List<Ingredient> ingredients,
                          List<PizzaSizeAndPrices> sizeAndPrices) {
    var item = new Item();
    var selectedPizza = PizzaService.getPizzaFromListById(pizzas, itemDTO.getPizzaId());
    var selectedSizeAndPrice = SizeAndPriceService.getSizeAndPriceFromListById(sizeAndPrices,
        itemDTO.getSizeAndPriceID());
    var modifyIngredients = ItemModifyIngredientService.getModifyIngredientsFromListById(ingredients,
        itemDTO.getModifyIngredientsIDs());
    for(var ingredient : modifyIngredients) {
      ingredient.setItem(item);
    }
    item.setPizza(selectedPizza);
    item.setSizeAndPrices(selectedSizeAndPrice);
    item.setItemModifyIngredients(modifyIngredients);
    return item;
  }

  private RequiredData loadData() {
    var pizzas = pizzaRepository.findAll();
    var ingredients = ingredientRepository.findAll();
    var sizeAndPrices = priceRepository.findAll();
    return new RequiredData(pizzas, ingredients, sizeAndPrices);
  }

  public void saveItem(Item item) {
    em.getTransaction().begin();
    em.persist(item);
    em.flush();
    for (var i: item.getItemModifyIngredients())
    {
      i.setItem(item);
    }
    item.setItemModifyIngredients(item.getItemModifyIngredients());
    em.persist(item);
    em.getTransaction().commit();
    em.close();
  }

  public void saveItems(List<Item> items) {
    em.getTransaction().begin();
    for (Item item : items) {
      em.persist(item);
    }
    em.getTransaction().commit();
    em.close();
  }

  private static class RequiredData {
    List<Pizza> pizzas;
    List<Ingredient> ingredients;
    List<PizzaSizeAndPrices> sizeAndPrices;

    RequiredData(List<Pizza> pizzas, List<Ingredient> ingredients, List<PizzaSizeAndPrices> sizeAndPrices) {
      this.pizzas = pizzas;
      this.ingredients = ingredients;
      this.sizeAndPrices = sizeAndPrices;
    }
  }

}
