package servlet.DTO.Payu;

import lombok.Data;

@Data
public class OrderPayu {
  private String notifyUrl;
  private String customerIp;
  private String merchantPosId;
  private String description;
  private String currencyCode;
  private int totalAmount;
  private String extOrderId;
  private BuyerPayu buyer;
  private ProductPayu products;

  private OrderPayu(Builder builder) {
    this.notifyUrl = builder.notifyUrl;
    this.customerIp = builder.customerIp;
    this.merchantPosId = builder.merchantPosId;
    this.description = builder.description;
    this.currencyCode = builder.currencyCode;
    this.totalAmount = builder.totalAmount;
    this.extOrderId = builder.extOrderId;
    this.buyer = builder.buyer;
    this.products = builder.products;
  }

  public static class Builder {
    private String notifyUrl;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private int totalAmount;
    private String extOrderId;
    private BuyerPayu buyer;
    private ProductPayu products;


    public Builder(int totalAmount, String customerIp) {
      this.totalAmount = totalAmount;
      this.customerIp = customerIp;
    }

    public Builder SetNotifyUrl(String notifyUrl) {
      this.notifyUrl = notifyUrl;
      return this;
    }

    public Builder SetCustomerIp(String customerIp) {
      this.customerIp = customerIp;
      return this;
    }

    public Builder SetMerchantPosId(String merchantPosId) {
      this.merchantPosId = merchantPosId;
      return this;
    }

    public Builder SetDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder SetCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
      return this;
    }

    public Builder SetTotalAmount(int totalAmount) {
      this.totalAmount = totalAmount;
      return this;
    }

    public Builder SetExtOrderId(String extOrderId) {
      this.extOrderId = extOrderId;
      return this;
    }

    public Builder SetBuyer(BuyerPayu buyer) {
      this.buyer = buyer;
      return this;
    }

    public Builder SetProducts(ProductPayu products) {
      this.products = products;
      return this;
    }

    public OrderPayu build() {
      return new OrderPayu(this);
    }

  }
}
