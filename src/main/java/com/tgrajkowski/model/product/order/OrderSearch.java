package com.tgrajkowski.model.product.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderSearch {
    private String productTitle;
    private String dateFrom;
    private String dateTo;
    private String status;
    private String userLogin;
}
