package com.tgrajkowski.model.product.bought;

import java.io.Serializable;

import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.order.ProductsOrder;
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
// usuń total price
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
