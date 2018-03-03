package com.tgrajkowski.model.bucket;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserBucketDto {
    private Long id;
    private String login;
    private List<Long> listId;
}
