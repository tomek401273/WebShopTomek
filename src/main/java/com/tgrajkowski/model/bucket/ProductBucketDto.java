package com.tgrajkowski.model.bucket;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductBucketDto {
    private Long id;
    private Integer price;
    private String title;
    private String description;
    private String ImageLink;
    private int amount;
}
