package servlet.DTO.Payu;

import lombok.Data;

@Data
public class BuyerPayu {
  private String email;
  private String phone;
  private String firstName;
  private String lastName;
  private String language;
  //private String address;
}
