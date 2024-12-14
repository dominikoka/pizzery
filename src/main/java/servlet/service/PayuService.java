package servlet.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import servlet.DTO.Payu.OrderPayu;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PayuService {
  @SneakyThrows
  public static JsonNode getToken()
      throws URISyntaxException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = null;
    //
    String url = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize";
    String data_to_send = "grant_type=client_credentials&client_id=486613&client_secret" +
        "=9d4a5e8ca15ecf3605f4f83d5dce76c9";
    try {
      URL obj = new URI(url).toURL(); // Making an object to point to the API URL
      HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
        os.writeBytes(data_to_send);
        os.flush();
      }
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        StringBuilder response = new StringBuilder();
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
          String line;
          while ((line = reader.readLine()) != null) {
            response.append(line); // Adds every line to response till the end of file.
          }
        }
        System.out.println("Response: " + response.toString());
        jsonNode = mapper.readTree(response.toString());
      } else {
        System.out.println("Error: HTTP Response code - " + responseCode);
      }
      connection.disconnect();
    } catch (IOException e) {
      System.out.println(e.toString());
      e.printStackTrace();
    }
    return jsonNode;
  }


  public String CreateOrder(OrderPayu orderPayu, JsonNode jsonToken) {
    String tokenUrl = "https://secure.snd.payu.com/api/v2_1/orders";
    var idOrder = orderPayu.getExtOrderId();
    var authorizationBearer = jsonToken.path("access_token").toString().replace("\"","");
    String price = String.valueOf(orderPayu.getProducts().getUnitPrice() * 100);

    var res = "";
    String requestBody = "{\n" +
        "    \"notifyUrl\": \"https://your.eshop.com/notify\",\n" +
        "    \"customerIp\": \"127.0.0.1\",\n" +
        "    \"merchantPosId\": \"476464\",\n" +
        "    \"description\": \"" + orderPayu.getExtOrderId() + "\",\n" +
        "    \"currencyCode\": \"PLN\",\n" +
        "    \"totalAmount\": \"" + price + "\",\n" +
        "    \"extOrderId\": \"" + idOrder + "\",\n" +
        "    \"buyer\": {\n" +
        "        \"email\": \"" + orderPayu.getBuyer().getEmail() + "\",\n" +
        "        \"phone\": \"" + orderPayu.getBuyer().getPhone() + "\",\n" +
        "        \"firstName\": \"" + orderPayu.getBuyer().getFirstName() + "\",\n" +
        "        \"lastName\": \"" + orderPayu.getBuyer().getLastName() + "\",\n" +
        "        \"language\": \"pl\"\n" +
        "    },\n" +
        "    \"products\": [\n" +
        "        {\n" +
        "            \"name\": \"" + orderPayu.getProducts().getName() + "\",\n" +
        "            \"unitPrice\": \"" + orderPayu.getProducts().getUnitPrice() + "\",\n" +
        "            \"quantity\": \"" + orderPayu.getProducts().getQuantity() + "\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";
    var authorizationToken = "Bearer " + authorizationBearer;
    try {
      URL url = new URL(tokenUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Authorization", authorizationToken);
      connection.setDoOutput(true);
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = requestBody.getBytes("utf-8");
        os.write(input, 0, input.length);
      }
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      in.close();
      res = connection.getURL().toString();
    } catch (IOException e) {
      e.printStackTrace();
      ;
    }
    return res;
  }
}
