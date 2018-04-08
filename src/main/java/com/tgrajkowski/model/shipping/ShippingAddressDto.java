package com.tgrajkowski.model.shipping;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShippingAddressDto {
    private String login;
    private String country;
    private String city;
    private String postCode;
    private String street;
    private String name;
    private String surname;
    private String supplier;
    private String code;

    public ShippingAddressDto(String country, String city, String postCode, String street, String name, String surname, String supplier) {
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.name = name;
        this.surname = surname;
        this.supplier = supplier;
    }
}
