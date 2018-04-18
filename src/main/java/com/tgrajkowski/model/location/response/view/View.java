package com.tgrajkowski.model.location.response.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgrajkowski.model.location.response.view.result.Result;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class View {
    @JsonProperty("_type")
    String type;

    @JsonProperty("ViewId")
    int viewId;

    @JsonProperty("Result")
    private Result[] result;

}
