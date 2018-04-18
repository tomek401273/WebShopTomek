package com.tgrajkowski.model.shipping;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShippingAddressDto {
    private String login;
    private String postalCode;
    private String name;
    private String surname;
    private String supplier;
    private String code;
    private String country;
    private String state;
    private String county;
    private String city;
    private String district;
    private String subdistrict;
    private String street;
    private String search;
//    private String postalCode;


    public ShippingAddressDto(String country, String city, String postalCode, String street, String name, String surname, String supplier) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.name = name;
        this.surname = surname;
        this.supplier = supplier;
    }
}
