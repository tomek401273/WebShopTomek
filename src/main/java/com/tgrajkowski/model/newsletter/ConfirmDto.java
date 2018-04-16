package com.tgrajkowski.model.newsletter;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfirmDto {
    private String email;
    private String confirmCode;
    private boolean isDiscount;
    private String discountCode;
}
