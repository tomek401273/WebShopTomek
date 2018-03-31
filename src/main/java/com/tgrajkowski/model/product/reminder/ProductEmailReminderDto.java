package com.tgrajkowski.model.product.reminder;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductEmailReminderDto {
    private Long productId;
    private String email;
}
