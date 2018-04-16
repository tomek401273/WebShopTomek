package com.tgrajkowski.model.location.response.view.result.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgrajkowski.model.location.response.view.result.location.address.Address;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("Address")
    private Address address;
}
