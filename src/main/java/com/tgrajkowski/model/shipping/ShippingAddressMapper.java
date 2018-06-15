package com.tgrajkowski.model.shipping;

import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class ShippingAddressMapper {
//    public ShippingAddress mappToShippedAdderss(ShippingAddressDto shippingAddressDto) {
//        ShippingAddress shippingAddress = new ShippingAddress(shippingAddressDto.getCountry(), shippingAddressDto.getCity(), shippingAddressDto.getPostalCode(),
//                shippingAddressDto.getStreet(), shippingAddressDto.getName(), shippingAddressDto.getSurname(),
//                shippingAddressDto.getSupplier());
//        shippingAddress.setState(shippingAddressDto.getState());
//        shippingAddress.setCounty(shippingAddressDto.getCounty());
//        shippingAddress.setDistrict(shippingAddress.getDistrict());
//        shippingAddress.setSubdistrict(shippingAddress.getSubdistrict());
//
//        return shippingAddress;
//    }

    public ShippingAddressDto mapToShippingAddressDto (ShippingAddress shippingAddress) {
        System.out.println("ShippingAddress: "+shippingAddress.toString());
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
        System.out.println("ShippingAddresMapper: "+shippingAddressDto.toString());
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
