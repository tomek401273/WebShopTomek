package com.tgrajkowski.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    String code;

    @Column
    String name;

    @OneToMany(
            targetEntity = Product.class,
            mappedBy = "status",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    List<Product> productList = new ArrayList<>();

    public ProductStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
