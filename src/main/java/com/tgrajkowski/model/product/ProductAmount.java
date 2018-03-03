package com.tgrajkowski.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//@NamedNativeQuery(
//        name = "ProductAmount.findSpecyfic",
//        query = "SELECT COUNT(bucket_id) as count, product_id FROM product_amount where bucket_id =3 group by product_id",
//
//        resultClass = ProductAmount.class
//)
@NamedNativeQuery(
        name = "ProductAmount.findAllCount",
        query = "SELECT COUNT(bucket_id) FROM product_amount where bucket_id =3 group by product_id",

        resultClass = ProductAmount.class
)





@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class ProductAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "BUCKET_ID")
    private Bucket bucket;

    public ProductAmount(Product product) {
        this.product = product;
    }
}
