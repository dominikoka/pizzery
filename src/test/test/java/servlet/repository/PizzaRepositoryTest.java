package test.java.servlet.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;
import org.junit.Test;
import servlet.repository.IngredientRepository;
import servlet.repository.OrderRepository;
import servlet.repository.PizzaRepository;
import servlet.service.IngredientSelectionService;
import servlet.service.OrderService;
import servlet.service.PizzaService;
import servlet.singeleton.EntityManagerFactorySingeleton;

import java.util.*;


public class PizzaRepositoryTest {
  public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm) {
    // Create a list from elements of HashMap
    List<Map.Entry<Integer, Double>> list =
        new LinkedList<Map.Entry<Integer, Double>>(hm.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
      public int compare(Map.Entry<Integer, Double> o1,
                         Map.Entry<Integer, Double> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // put data from sorted list to hashmap
    HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
    for (Map.Entry<Integer, Double> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }

  @Test
  public void findAllShouldReturnAllPizza() {
    //wez pizze ze skladnikami
    int idPizza = 1;
    PizzaRepository pr = new PizzaRepository();
//    List<Pizza> pizzas = pr.findAll();
//    for (Pizza p : pizzas) {
//      System.out.println("pizza");
//      System.out.println(p);
//      System.out.println("ingredients");
//      for (Ingredient i : p.getIngredients()) {
//        System.out.println(i);
//      }
//      System.out.println("");
//    }
    var z = pr.findById(idPizza);

    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    IngredientRepository in = new IngredientRepository(em);
    var ingrediens = in.findIngredientsWhereIdPizza(1);
    var extraIngredients = in.findOptionalIngredientsWhereIdPizza(1);
    System.out.println("moja pizza: " + z);
    System.out.println("skladniki: ");
    ingrediens.forEach(System.out::println);
    System.out.println("skladniki mozliwe do kupienia: ");
    extraIngredients.forEach(System.out::println);

  }

  @Test
  public void test2() {
    int idPizza = 1;
    int idIngredient = 5;

//    PizzaRepository pizzaRepository = new PizzaRepository();
//    var pizza = pizzaRepository.findById(idPizza);
//    for (Ingredient ingredient : pizza.getIngredients()) {
//      System.out.println(ingredient);
//    }
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    IngredientRepository ingredientRepository = new IngredientRepository(em);
    var aad = ingredientRepository.findOptionalIngredientsWhereIdPizza(1);
    System.out.println("aaa");


  }

  @Test
  public void takeAllIngredientsAndSelectUsed() {
    int idPizza = 1;
    PizzaService pizzaService = new PizzaService();
    var pizza = pizzaService.getPizzaById(idPizza);
    IngredientSelectionService ingredientSelectionService = new IngredientSelectionService();
    var ingredients = ingredientSelectionService.selectedIngredients(pizza);

    System.out.println("aaa");
  }

  @Test
  public void addPizzaIngredients() {
    int idPizza = 1;
    int idIngredient = 4;

    PizzaService pizzaService = new PizzaService();
    pizzaService.addPizzaIngredients(idPizza, idIngredient);


  }

  @SneakyThrows
  @Test
  public void json() {
    String json = "{"
        + "\"osoby\": ["
        + "{"
        + "\"imie\": \"Anna\","
        + "\"nazwisko\": \"Kowalska\","
        + "\"wiek\": 28,"
        + "\"adres\": {"
        + "\"ulica\": \"Marszałkowska\","
        + "\"numer\": 15,"
        + "\"miasto\": \"Warszawa\","
        + "\"kod_pocztowy\": \"00-001\""
        + "},"
        + "\"zainteresowania\": ["
        + "\"programowanie\","
        + "\"sztuka\","
        + "\"podróże\""
        + "]"
        + "},"
        + "{"
        + "\"imie\": \"Jan\","
        + "\"nazwisko\": \"Nowak\","
        + "\"wiek\": 35,"
        + "\"adres\": {"
        + "\"ulica\": \"Kwiatowa\","
        + "\"numer\": 20,"
        + "\"miasto\": \"Kraków\","
        + "\"kod_pocztowy\": \"30-001\""
        + "},"
        + "\"zainteresowania\": ["
        + "\"sport\","
        + "\"muzyka\""
        + "]"
        + "},"
        + "{"
        + "\"imie\": \"Piotr\","
        + "\"nazwisko\": \"Wiśniewski\","
        + "\"wiek\": 42,"
        + "\"adres\": {"
        + "\"ulica\": \"Długa\","
        + "\"numer\": 5,"
        + "\"miasto\": \"Gdańsk\","
        + "\"kod_pocztowy\": \"80-001\""
        + "},"
        + "\"zainteresowania\": ["
        + "\"fotografia\","
        + "\"wędkarstwo\""
        + "]"
        + "}"
        + "]"
        + "}";

    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(json);

    var persons = jsonNode.path("osoby");
    for (JsonNode person : persons) {
      var imie = person.path("imie").asText();
      var nazwisko = person.path("nazwisko");
      var wiek = person.path("wiek");
      var adres = person.path("adres");
      System.out.printf("%s,%s,%s", imie, nazwisko, wiek);
      System.out.println();

      var ulica = adres.path("ulica");
      var numer = adres.path("numer");
      var miasto = adres.path("miasto");
      var zainteresowania = person.path("zainteresowania");
      System.out.printf("%s,%s,%s", ulica, numer, miasto);
      System.out.println();
      for (var zainteresowanie : zainteresowania) {
        System.out.printf(zainteresowanie.asText() + ", ");
      }
      System.out.println();
    }
  }

  @Test
  public void maptest() {
    HashMap<Integer, Double> pizzaMap = new HashMap<>();
    pizzaMap.put(3, 32.0);
    pizzaMap.put(6, 32.0);
    pizzaMap.put(2, 13.0);
    System.out.println(pizzaMap);
    var sortedPM = sortByValue(pizzaMap);
    System.out.println(sortedPM);

  }

  @Test
  public void createOrder() {
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OrderRepository orderRepository = new OrderRepository(em);
    var order = orderRepository.createOrder();
    System.out.println("aaa");
  }

  @Test
  public void takePizzas() {
    List<Integer> pizzas = Arrays.asList(1, 2, 3);
    PizzaRepository pizzaRepository = new PizzaRepository();
    var pizzase = pizzaRepository.findByIds(pizzas);
    //var pizza = pizzaRepository.findById(1);

    System.out.println("aaaa");
  }
  @Test
  public void getPriceInOrder()
  {
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OrderRepository orderRepository = new OrderRepository(em);
    var priceOrder = orderRepository.getPriceIdOrder(54);
    System.out.println(priceOrder);

  }

  @Test
  public void changeOrderPrice()
  {
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OrderRepository orderRepository = new OrderRepository(em);
    orderRepository.updatePriceOrder(53,200);
  }
  @Test
  public void getOrders() {
    var em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OrderRepository orderRepository = new OrderRepository(em);
    OrderService orderService = new OrderService();
    var orders = orderRepository.getOrders();
    var OrdersDTO = orderService.mapOrdersToDTO(orders);
    System.out.println("aaa");
  }
}
