package com.tgrajkowski.model.product.order;

import com.tgrajkowski.model.product.bought.ProductBoughtDto;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductsOrderDto {

    private Long id;
    private int totalValue;
    private int totalAmount;
    private String boughtDate;
    private boolean isPaid;
    private boolean isPrepared;
    private boolean isSend;
    private String userLogin;
    private List<ProductBoughtDto> productBoughts = new ArrayList<>();
    private ShippingAddressDto shippingAddressDto;

    public ProductsOrderDto(Long id, int totalValue, int totalAmount, String boughtDate, boolean isPaid, boolean isPrepared, boolean isSend, String userLogin, List<ProductBoughtDto> productBoughts, ShippingAddressDto shippingAddressDto) {
        this.id = id;
        this.totalValue = totalValue;
        this.totalAmount = totalAmount;
        this.boughtDate = boughtDate;
        this.isPaid = isPaid;
        this.isPrepared = isPrepared;
        this.isSend = isSend;
        this.userLogin = userLogin;
        this.productBoughts = productBoughts;
        this.shippingAddressDto = shippingAddressDto;
    }

    public ProductsOrderDto(Long id, int totalValue, int totalAmount, String boughtDate, boolean isPaid, boolean isPrepared, boolean isSend, String userLogin, List<ProductBoughtDto> productBoughts) {
        this.id = id;
        this.totalValue = totalValue;
        this.totalAmount = totalAmount;
        this.boughtDate = boughtDate;
        this.isPaid = isPaid;
        this.isPrepared = isPrepared;
        this.isSend = isSend;
        this.userLogin = userLogin;
        this.productBoughts = productBoughts;
    }
}
