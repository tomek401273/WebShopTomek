package com.tgrajkowski.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@IdClass(Product_BucketPK.class)
public class Product_Bucket implements Serializable {
    @Id
    private Long productIdA;

    @Id
    private Long bucketIdA;

    @ManyToOne
    @JoinColumn(name = "product_title")
    private Product product;

    @ManyToOne
    private Bucket bucket;

    @Column
    private int amount;

    public Product_Bucket() {
    }

    public Product_Bucket(Product product, Bucket bucket) {
        this.product = product;
        this.bucket = bucket;
        this.productIdA= product.getId();
        this.bucketIdA = bucket.getId();
    }


}
