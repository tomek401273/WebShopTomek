package com.tgrajkowski.model.product.comment;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private String login;
    private String createdDate;
    private String message;
    private Long productId;

    public CommentDto(String login, String message, Long productId) {
        this.login = login;
        this.message = message;
        this.productId = productId;
    }
}
