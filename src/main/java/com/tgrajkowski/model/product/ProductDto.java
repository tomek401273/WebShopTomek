package com.tgrajkowski.model.product;

import com.tgrajkowski.model.product.comment.CommentDto;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductDto {

    private Long id;
    @Min(0)
    private BigDecimal price;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String ImageLink;
    @Min(0)
    private int totalAmount;
    @Min(0)
    private int availableAmount;
    private String statusCode;
    private String statusMessage;
    private BigDecimal sumMarks;
    private BigDecimal countMarks;
    private BigDecimal marksAverage;
    private List<CommentDto> commentDtos;
    private String category;
    private String directLink;

    public ProductDto(Long id, BigDecimal price, String title, String description, String imageLink, int totalAmount, int availableAmount) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.description = description;
        ImageLink = imageLink;
        this.totalAmount = totalAmount;
        this.availableAmount = availableAmount;
    }
}
