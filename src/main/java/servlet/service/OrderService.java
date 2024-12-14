package servlet.service;

import servlet.DTO.Create.ItemCreateDTO;
import servlet.DTO.Read.ItemReadDTO;
import servlet.DTO.Read.OrderReadDTO;
import servlet.model.Item;
import servlet.model.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderService {

  public ItemService itemService = new ItemService(new IngredientService(), new ItemModifyIngredientService());


  public List<OrderReadDTO> mapOrdersToDTO(List<Order> orders) {
    List<OrderReadDTO> orderReadDTOs = new ArrayList<OrderReadDTO>();
    for (Order order : orders) {
      orderReadDTOs.add(mapOrderToDTO(order));
    }
    return orderReadDTOs;
  }

  public OrderReadDTO mapOrderToDTO(Order order) {
    OrderReadDTO dtoRead = new OrderReadDTO();
    dtoRead.setId(Math.toIntExact(order.getId()));
    dtoRead.setDate(order.getDate());
    dtoRead.setIs_paid(order.getIs_paid());
    var itemsDTO = itemService.mapItemsToDTO(order.getItems());
    dtoRead.setItems(itemsDTO);
    dtoRead.setTotal_price(getTotalPriceFromDTO(itemsDTO, 0));
    return dtoRead;
  }

  public Double getTotalPriceFromDTO(Set<ItemReadDTO> itemsDTO, int discountPercentage) {
    double totalPrice = 0;
    for (var item : itemsDTO) {
      totalPrice += item.getTotalPrice();
    }
    BigDecimal roundedPrice = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
    return roundedPrice.doubleValue();
  }


  public double getTotalPriceFromItems(List<Item> itemsDTO, int i) {
    double res = 0;
    for (var item : itemsDTO) {
     res+=item.getSizeAndPrices().getPrice();
     for(var modifyIngredient: item.getItemModifyIngredients())
     {
       res+=modifyIngredient.getIngredient().getPrice();
     }
    }
    return res;
  }
}
