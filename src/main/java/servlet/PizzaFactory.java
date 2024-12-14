//package servlet;
//
//import lombok.Getter;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class PizzaFactory {
//
//
//  public static Pizza createPizza(String name, int size) {
//    Pizza.PizzaBuilder pizzaBuilder = null;
//
//    if (name.equalsIgnoreCase("pepperoni")) {
//      pizzaBuilder = new Pizza.PizzaBuilder("pepperoni", size)
//          .addIngredient("cheese")
//          .addIngredient("tomato")
//          .addIngredient("basil")
//          .addIngredient("cheese")
//          .addIngredient("salami")
//          .addIngredient("olives")
//          .addIngredient("cucumber")
//          .addIngredient("onion")
//          .addIngredient("pepper");
//    }
//    if (name.equalsIgnoreCase("margheritta")) {
//      pizzaBuilder = new Pizza.PizzaBuilder("margheritta", size)
//          .addIngredient("cheese")
//          .addIngredient("salami");
//    }
//    return pizzaBuilder.build();
//  }
//
////  public static List<String> getIngredientsPizza(String pizzaName) {
////    if(pizzaName.equalsIgnoreCase("pepperoni")) {
////      return ingredientsPepperoni;
////    }
////    if(pizzaName.equalsIgnoreCase("margheritta")) {
////      return ingredientsMargheritta;
////    }
////    return null;
////  }
//}
//
