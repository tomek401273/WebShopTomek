package com.tgrajkowski.model.location.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgrajkowski.model.location.response.view.View;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    @JsonProperty("View")
    private View [] view;
}
