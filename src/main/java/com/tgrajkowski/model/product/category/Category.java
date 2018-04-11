package com.tgrajkowski.model.product.category;

import com.tgrajkowski.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @OneToMany(
            targetEntity = Product.class,
            mappedBy = "category",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    List<Product> productList = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }
}
