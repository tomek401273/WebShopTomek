package com.tgrajkowski.model.product;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Product.findProductContainstTitleWithLetters",
                query="SELECT * FROM product WHERE title LIKE concat('%',:LETTERS,'%')",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = "Product.findProductWithPriceBetween",
                query = "SELECT * FROM product WHERE price >=:ABOVE AND price <=:BELOW",
                resultClass = Product.class
        )
})

@Getter
@Setter
@Entity
@NoArgsConstructor
//@AllArgsConstructor
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
            fetch = FetchType.LAZY
    )
    private List<ProductBucket> productBuckets = new ArrayList<>();

    @OneToMany(
            targetEntity = ProductBought.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductBought> productBoughts= new ArrayList<>();

    public Product(Integer price, String title, String description, String imageLink, int totalAmount, int availableAmount) {
        this.price = price;
        this.title = title;
        this.description = description;
        ImageLink = imageLink;
        this.totalAmount = totalAmount;
        this.availableAmount = availableAmount;
    }
}
