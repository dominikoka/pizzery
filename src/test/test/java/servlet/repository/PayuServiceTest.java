package test.java.servlet.repository;

import lombok.SneakyThrows;
import org.junit.Test;
import servlet.DTO.Payu.BuyerPayu;
import servlet.DTO.Payu.OrderPayu;
import servlet.DTO.Payu.ProductPayu;
import servlet.service.PayuService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PayuServiceTest {

  @SneakyThrows
  @Test
  public void createTokenShouldReceiveJsonToken() {
    //given
    var token = PayuService.getToken();
    System.out.println(token);
    //when
    //them
    assertNotNull(token, "JSON token should not be null");

  }

  @SneakyThrows
  @Test
  public void Access_TokenShouldReturnAccess_Token() {
    var token = PayuService.getToken();
    var authorizationBearer = token.path("access_token");

    assertNotNull(authorizationBearer, "JSON token should not be null");

  }

  @SneakyThrows
  @Test
  public void createOrderSHouldReturnPayLink() {
    //given
    var token = PayuService.getToken();
    var buyer = new BuyerPayu();
    buyer.setEmail("asd@gmail.com");
    buyer.setFirstName("domino");
    buyer.setLastName("scott");
    buyer.setLanguage("PL");
    buyer.setPhone("0730432932");

    var product = new ProductPayu();
    product.setName("pizza");
    product.setQuantity(2);
    product.setUnitPrice(20000);

    var order =
        new OrderPayu.Builder(product.getUnitPrice(), "47")
            .SetExtOrderId("28")
            .SetBuyer(buyer)
            .SetCustomerIp("127.0.0.1")
            .SetDescription("desc")
            .SetMerchantPosId("44")
            .SetCurrencyCode("PLN")
            .SetProducts(product)
            .SetNotifyUrl("https://your.eshop.com/notify")
            .build();

    //when
    PayuService payuService = new PayuService();
    payuService.CreateOrder(order, token);
  }
}


