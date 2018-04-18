package com.tgrajkowski.model.location.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("alpha2Code")
    private String alpha2Code;

    @JsonProperty("alpha3Code")
    private String alpha3Code;

    private boolean approved;

    public CountryDto(String name, String code, boolean approved) {
        this.name = name;
        this.code = code;
        this.approved = approved;
    }
}
