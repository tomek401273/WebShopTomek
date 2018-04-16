package com.tgrajkowski.model.location.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityDto {
    @JsonProperty("city")
    private String city;
}
