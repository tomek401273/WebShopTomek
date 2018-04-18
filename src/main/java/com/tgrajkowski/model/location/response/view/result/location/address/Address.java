package com.tgrajkowski.model.location.response.view.result.location.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    @JsonProperty("Label")
    private String label;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("State")
    private String state;

    @JsonProperty("County")
    private String county;

    @JsonProperty("City")
    private String city;

    @JsonProperty("District")
    private String district;

    @JsonProperty("Subdistrict")
    private String subdistrict;

    @JsonProperty("Street")
    private String street;

    @JsonProperty("PostalCode")
    private String postalCode;


}
