package com.tgrajkowski.model.product.order;

import com.tgrajkowski.model.product.bought.ProductBoughtDto;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductsOrderDto {

    private Long id;
    private int totalValue;
    private int totalAmount;
    private String boughtDate;
    private String userLogin;
    private List<ProductBoughtDto> productBoughts = new ArrayList<>();
    private ShippingAddressDto shippingAddressDto;
    private String status;
    private String linkDelivery;
    private String sendDate;
    private String deliveryDate;
    private String statusCode;

    public ProductsOrderDto(
            Long id,
            int totalValue,
            int totalAmount,
            String boughtDate,
            String userLogin,
            List<ProductBoughtDto> productBoughts,
            ShippingAddressDto shippingAddressDto,
            String status) {
        this.id = id;
        this.totalValue = totalValue;
        this.totalAmount = totalAmount;
        this.boughtDate = boughtDate;
        this.userLogin = userLogin;
        this.productBoughts = productBoughts;
        this.shippingAddressDto = shippingAddressDto;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductsOrderDto)) return false;

        ProductsOrderDto that = (ProductsOrderDto) o;

        if (totalValue != that.totalValue) return false;
        if (totalAmount != that.totalAmount) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (boughtDate != null ? !boughtDate.equals(that.boughtDate) : that.boughtDate != null) return false;
        if (userLogin != null ? !userLogin.equals(that.userLogin) : that.userLogin != null) return false;
        if (productBoughts != null ? !productBoughts.equals(that.productBoughts) : that.productBoughts != null)
            return false;
        if (shippingAddressDto != null ? !shippingAddressDto.equals(that.shippingAddressDto) : that.shippingAddressDto != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (linkDelivery != null ? !linkDelivery.equals(that.linkDelivery) : that.linkDelivery != null) return false;
        if (sendDate != null ? !sendDate.equals(that.sendDate) : that.sendDate != null) return false;
        return deliveryDate != null ? deliveryDate.equals(that.deliveryDate) : that.deliveryDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + totalValue;
        result = 31 * result + totalAmount;
        result = 31 * result + (boughtDate != null ? boughtDate.hashCode() : 0);
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + (productBoughts != null ? productBoughts.hashCode() : 0);
        result = 31 * result + (shippingAddressDto != null ? shippingAddressDto.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (linkDelivery != null ? linkDelivery.hashCode() : 0);
        result = 31 * result + (sendDate != null ? sendDate.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        return result;
    }
}
