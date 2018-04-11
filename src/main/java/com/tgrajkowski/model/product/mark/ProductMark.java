package com.tgrajkowski.model.product.mark;

import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.user.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@IdClass(ProductMarkPK.class)
public class ProductMark implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column
    private int mark;

    public ProductMark() {
    }

    public ProductMark(Product product, Users user, int mark) {
        this.product = product;
        this.user = user;
        this.mark = mark;
    }
}
