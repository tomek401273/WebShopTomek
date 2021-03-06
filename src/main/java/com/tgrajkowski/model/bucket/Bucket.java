package com.tgrajkowski.model.bucket;

import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.user.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @OneToMany(
            targetEntity = ProductBucket.class,
            mappedBy = "bucket",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductBucket> productBuckets = new ArrayList<>();

}
