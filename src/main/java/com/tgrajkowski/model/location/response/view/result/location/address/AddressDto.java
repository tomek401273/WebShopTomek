package com.tgrajkowski.model.location.response.view.result.location.address;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AddressDto {
    private String label;
    private String country;
    private String state;
    private String county;
    private String city;
    private String district;
    private String subdistrict;
    private String street;
    private String postalCode;
}
