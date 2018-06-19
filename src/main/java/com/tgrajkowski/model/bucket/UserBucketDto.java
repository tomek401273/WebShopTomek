package com.tgrajkowski.model.bucket;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserBucketDto {
    private Long productId;
    private List<Long> productIdArray;

    public UserBucketDto(Long productId) {
        this.productId = productId;
    }
}


