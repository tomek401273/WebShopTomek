package com.tgrajkowski.model.product.order;

import lombok.AllArgsConstructor;
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
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String code;

    @Column
    private String name;

    @OneToMany(
            targetEntity = ProductsOrder.class,
            mappedBy = "status",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductsOrder> productsOrders = new ArrayList<>();

    public Status(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
