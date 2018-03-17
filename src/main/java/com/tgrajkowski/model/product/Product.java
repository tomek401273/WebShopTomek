package com.tgrajkowski.model.product;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Integer price;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String ImageLink;

    @Column
    private int totalAmount;

    @Column
    private int availableAmount;

    @OneToMany(
            targetEntity = ProductBucket.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    private List<ProductBucket> productBuckets = new ArrayList<>();

    public Product(Integer price, String title, String description, String imageLink, int totalAmount, int availableAmount) {
        this.price = price;
        this.title = title;
        this.description = description;
        ImageLink = imageLink;
        this.totalAmount = totalAmount;
        this.availableAmount = availableAmount;
    }
}
