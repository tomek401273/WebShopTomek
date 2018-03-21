package com.tgrajkowski.model.product;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@IdClass(ProductBoughtPK.class)
public class ProductBought implements Serializable {
    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productsOrder_id")
    private ProductsOrder productsOrder;

    @Column
    private int amount;

    @Column
    private int totalPrice;

    public ProductBought() {
    }

    public ProductBought(Product product, ProductsOrder productsOrder, int amount) {
        this.product = product;
        this.productsOrder = productsOrder;
        this.amount = amount;
        this.totalPrice =amount*product.getPrice();
    }
}
