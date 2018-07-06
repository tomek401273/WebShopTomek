package com.tgrajkowski.model.product;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.category.Category;
import com.tgrajkowski.model.product.comment.Comment;
import com.tgrajkowski.model.product.mark.ProductMark;
import com.tgrajkowski.model.product.reminder.ProductEmailReminder;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Product.findProductContainstTitleWithLetters",
                query = "SELECT * FROM product P, product_status S WHERE LOWER(P.title) LIKE concat('%', LOWER(:LETTERS), '%') AND P.status_id = S.id AND (S.code = 'sale' OR S.code = 'inaccessible') ORDER BY P.title ASC",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = "Product.findProductWithPriceBetween",
                query = "SELECT * FROM product WHERE price >=:ABOVE AND price <=:BELOW",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = "Product.getMaxProductPrice",
                query = "SELECT * FROM product WHERE price =(SELECT MAX(price) FROM product)",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = "Product.getProductTitleOnSale",
                query = "SELECT * FROM product P, product_status S WHERE P.status_id = S.id AND (S.code = 'sale' OR S.code = 'inaccessible')",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = "Product.getProductOnSaleAndInaccesiableAsc",
                query = "SELECT * FROM product P, product_status S WHERE P.status_id = S.id AND (S.code = 'sale' OR S.code = 'inaccessible') ORDER BY P.title ASC",
                resultClass = Product.class
        )
})

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "PRODUCT_ID", unique = true)
    private Long id;

    @Column
    private BigDecimal price;

    @Column
    private String title;

    @Column()
    private String description;

    @Column
    private String ImageLink;

    @Column
    private int totalAmount;

    @Column
    private int availableAmount;

    @Column(nullable = false)
    private boolean toDelete;

    @OneToMany(
            targetEntity = ProductBucket.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductBucket> productBuckets = new ArrayList<>();

    @OneToMany(
            targetEntity = ProductBought.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductBought> productBoughts = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private ProductStatus status;

    @ManyToMany(
            targetEntity = ProductEmailReminder.class,
            mappedBy = "products",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductEmailReminder> productEmailReminders = new ArrayList<>();

    @OneToMany(
            targetEntity = ProductMark.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductMark> productMarks = new ArrayList<>();

    @Column
    private BigDecimal averageMarks;

    @Column
    private BigDecimal sumMarks;

    @Column
    private BigDecimal countMarks;

    @Column
    private Date lastModification;

    @OneToMany(
            targetEntity = ShortDescription.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ShortDescription> shortDescriptions = new ArrayList<>();


    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY

    )
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(BigDecimal price, String title, String description, String imageLink, int totalAmount, int availableAmount, Date lastModification) {
        this.price = price;
        this.title = title;
        this.description = description;
        ImageLink = imageLink;
        this.totalAmount = totalAmount;
        this.availableAmount = availableAmount;
        this.lastModification = lastModification;
    }
}
