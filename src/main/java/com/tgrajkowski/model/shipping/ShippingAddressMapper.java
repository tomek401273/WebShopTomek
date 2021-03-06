package com.tgrajkowski.model.shipping;

import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class ShippingAddressMapper {

    public ShippingAddressDto mapToShippingAddressDto (ShippingAddress shippingAddress) {
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto(
                shippingAddress.getCountry(),
                shippingAddress.getCity(),
                shippingAddress.getPostCode(),
                shippingAddress.getStreet(),
                shippingAddress.getName(),
                shippingAddress.getSurname(),
                shippingAddress.getSupplier());
        shippingAddressDto.setState(shippingAddress.getState());
        shippingAddressDto.setCounty(shippingAddress.getCounty());
        shippingAddressDto.setDistrict(shippingAddress.getDistrict());
        shippingAddressDto.setSubdistrict(shippingAddress.getSubdistrict());
        shippingAddressDto.setHouse(shippingAddress.getHouse());
        shippingAddressDto.setApartment(shippingAddress.getApartment());

        return  shippingAddressDto;
    }

    public ShippingAddress mapToShippingAddresFromAddressDto(AddressDto addressDto, ShippingAddressDto shippingAddressDto) {
        ShippingAddress shippingAddress = new ShippingAddress(
                addressDto.getCountry(),
                addressDto.getCity(),
                addressDto.getPostalCode(),
                addressDto.getStreet(),
                shippingAddressDto.getName(),
                shippingAddressDto.getSurname(),
                shippingAddressDto.getSupplier()
        );
        shippingAddress.setState(addressDto.getState());
        shippingAddress.setCounty(addressDto.getCounty());
        shippingAddress.setDistrict(addressDto.getDistrict());
        shippingAddress.setSubdistrict(addressDto.getSubdistrict());
        shippingAddress.setHouse(shippingAddressDto.getHouse());
        shippingAddress.setApartment(shippingAddressDto.getApartment());
        return shippingAddress;
    }
}
