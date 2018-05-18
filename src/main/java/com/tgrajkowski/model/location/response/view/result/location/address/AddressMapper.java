package com.tgrajkowski.model.location.response.view.result.location.address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDto mapToAddresDto(Address address) {
        return new AddressDto(
                address.getLabel(),
                address.getCountry(),
                address.getState(),
                address.getCounty(),
                address.getCity(),
                address.getDistrict(),
                address.getSubdistrict(),
                address.getStreet(),
                address.getPostalCode()
        );


    }
}
