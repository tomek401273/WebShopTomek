package com.tgrajkowski.model.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@IdClass(ProductBucketPK.class)
public class ProductBucket implements Serializable {
//    @Id
//    private Long productIdA;
//
//    @Id
//    private Long bucketIdA;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name ="bucket_id" )
    private Bucket bucket;

    @Column
    private int amount;

    @Column
    private Date date;

    public ProductBucket() {
    }

    public ProductBucket(Product product, Bucket bucket, int amount) {
        this.product = product;
        this.bucket = bucket;
        this.amount = amount;
        this.date = new Date();
    }

}
