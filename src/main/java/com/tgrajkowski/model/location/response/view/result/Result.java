package com.tgrajkowski.model.location.response.view.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgrajkowski.model.location.response.view.result.location.Location;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @JsonProperty("Location")
    private Location location;
}
