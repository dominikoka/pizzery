package servlet.service;

import servlet.model.PizzaSizeAndPrices;

import java.util.List;
import java.util.NoSuchElementException;

public class SizeAndPriceService {
  public static PizzaSizeAndPrices getSizeAndPriceFromListById(List<PizzaSizeAndPrices> sizeAndPrices, int sizeAndPrice) {
    for (PizzaSizeAndPrices p : sizeAndPrices) {
      if (p.getId() == sizeAndPrice) {
        return p;
      }
    }
    throw new NoSuchElementException("Pizza with id " + sizeAndPrice + " not found");
  }
}
