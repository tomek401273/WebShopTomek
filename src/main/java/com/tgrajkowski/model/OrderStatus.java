package com.tgrajkowski.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderStatus {
    private String login;
    private Long orderId;
    private boolean paid;
    private boolean prepared;
    private boolean send;
    private String linkDelivery;
}
