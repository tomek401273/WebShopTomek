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
    private String login;
    private List<Long> productIdArray;
}


