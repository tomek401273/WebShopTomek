package com.tgrajkowski.model.product;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShortDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String attribute;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    public ShortDescription(String attribute, Product product) {
        this.attribute = attribute;
        this.product = product;
    }
}
