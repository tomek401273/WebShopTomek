package com.tgrajkowski.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    private String login;
    private Long orderId;
    private boolean isPaid;
}
