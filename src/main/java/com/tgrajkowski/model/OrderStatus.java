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
    private String status;
    private String linkDelivery;
}
