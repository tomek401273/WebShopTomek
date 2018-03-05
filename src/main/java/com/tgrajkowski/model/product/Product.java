package com.tgrajkowski.model.product;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @OneToMany(
            targetEntity = ProductAmount.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductAmount> productAmounts = new ArrayList<>();

    public Product(Integer price, String title, String description, String imageLink) {
        this.price = price;
        this.title = title;
        this.description = description;
        ImageLink = imageLink;
    }
}
