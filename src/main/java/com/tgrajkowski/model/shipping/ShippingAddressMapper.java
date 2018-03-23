package com.tgrajkowski.model.shipping;

public class ShippingAddressMapper {
    public ShippingAddress mappToShippedAdderss(ShippingAddressDto shippingAddressDto) {
        return new ShippingAddress(shippingAddressDto.getCountry(), shippingAddressDto.getCity(), shippingAddressDto.getPostCode(),
                shippingAddressDto.getStreet(), shippingAddressDto.getName(), shippingAddressDto.getSurname(),
                shippingAddressDto.getSupplier());
    }

    public ShippingAddressDto mapToShippingAddressDto (ShippingAddress shippingAddress) {

        return new ShippingAddressDto(
                shippingAddress.getCountry(),
                shippingAddress.getCity(),
                shippingAddress.getPostCode(),
                shippingAddress.getStreet(),
                shippingAddress.getName(),
                shippingAddress.getSurname(),
                shippingAddress.getSupplier());
    }
}
