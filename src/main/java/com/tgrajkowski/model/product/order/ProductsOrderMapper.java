package com.tgrajkowski.model.product.order;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bought.ProductBoughtDto;
import com.tgrajkowski.model.product.bought.ProductBoughtMapper;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.model.shipping.ShippingAddressMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductsOrderMapper {
    private ProductBoughtMapper productBoughtMapper = new ProductBoughtMapper();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ShippingAddressMapper shippingAddressMapper= new ShippingAddressMapper();

    public ProductsOrderDto mapToProductsOrderDto(ProductsOrder productsOrder) {
        List<ProductBoughtDto> productBoughtDtos =
                productBoughtMapper
                        .mapToProductBoughtDtoList(productsOrder.getProductBoughts());
//        System.out.println("shipping order address mapper: "+productsOrder.getShippingAddress().getCity());

        ShippingAddressDto shippingAddressDto = shippingAddressMapper.mapToShippingAddressDto(productsOrder.getShippingAddress());


        return new ProductsOrderDto(
                productsOrder.getId(),
                productsOrder.getTotalValue(),
                productsOrder.getTotalAmount(),
                simpleDateFormat.format(productsOrder.getBoughtDate()),
                productsOrder.isPaid(),
                productsOrder.isPrepared(),
                productsOrder.isSend(),
                productsOrder.getUser().getLogin(),
                productBoughtDtos,
                shippingAddressDto
        );
    }

    public List<ProductsOrderDto> mapToProductsOrderDtoList(List<ProductsOrder> productsOrders) {
        List<ProductsOrderDto> productsOrderDtos = new ArrayList<>();
        for (ProductsOrder productsOrder : productsOrders) {
            productsOrderDtos.add(mapToProductsOrderDto(productsOrder));
        }
        return productsOrderDtos;
    }
}
