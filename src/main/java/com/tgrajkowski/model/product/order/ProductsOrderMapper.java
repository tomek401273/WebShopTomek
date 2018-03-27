package com.tgrajkowski.model.product.order;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bought.ProductBoughtDto;
import com.tgrajkowski.model.product.bought.ProductBoughtMapper;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.model.shipping.ShippingAddressMapper;
import org.hibernate.boot.jaxb.SourceType;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProductsOrderMapper {
    private ProductBoughtMapper productBoughtMapper = new ProductBoughtMapper();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ShippingAddressMapper shippingAddressMapper = new ShippingAddressMapper();
    private String status;

    private String sendDateFormated;
    private String deliveryDateFromated;

    public ProductsOrderDto mapToProductsOrderDto(ProductsOrder productsOrder) {
        List<ProductBoughtDto> productBoughtDtos =
                productBoughtMapper
                        .mapToProductBoughtDtoList(productsOrder.getProductBoughts());

        ShippingAddressDto shippingAddressDto = shippingAddressMapper.mapToShippingAddressDto(productsOrder.getShippingAddress());
        status = productsOrder.getStatus().getName();



        ProductsOrderDto productsOrderDto = new ProductsOrderDto(
                productsOrder.getId(),
                productsOrder.getTotalValue(),
                productsOrder.getTotalAmount(),
                simpleDateFormat.format(productsOrder.getBoughtDate()),
                productsOrder.getUser().getLogin(),
                productBoughtDtos,
                shippingAddressDto,
                status
        );

        if (productsOrder.getStatus().getCode().equals("send")) {

            sendDateFormated = simpleDateFormat.format(productsOrder.getSendDate());

            LocalDate sendDateLocalDate = productsOrder.getSendDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate deliveryDate= sendDateLocalDate.plusDays(3);
            deliveryDateFromated= deliveryDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            productsOrderDto.setSendDate(sendDateFormated);
            productsOrderDto.setLinkDelivery(productsOrder.getLinkDelivery());
            productsOrderDto.setDeliveryDate(deliveryDateFromated);
        }

        return productsOrderDto;
    }

    public List<ProductsOrderDto> mapToProductsOrderDtoList(List<ProductsOrder> productsOrders) {
        List<ProductsOrderDto> productsOrderDtos = new ArrayList<>();
        for (ProductsOrder productsOrder : productsOrders) {
            productsOrderDtos.add(mapToProductsOrderDto(productsOrder));
        }
        return productsOrderDtos;
    }
}
