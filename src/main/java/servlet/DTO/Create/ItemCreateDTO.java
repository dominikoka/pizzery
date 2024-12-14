package servlet.DTO.Create;

import lombok.Data;

import java.util.List;

@Data
public class ItemCreateDTO {
  private int pizzaId;
  private int sizeAndPriceID;
  private int orderId;
  List<Integer> modifyIngredientsIDs;

//  public double getTotalPrice()
//  {
//    double totalPrice = 0;
//    totalPrice+=
//  }
}


