package com.tgrajkowski.model.newsletter;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubscriberDto {
    private String name;
    protected String email;
}
