package com.tgrajkowski.model.product.order;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.shipping.ShippingAddress;
import com.tgrajkowski.model.user.Users;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedNativeQuery(
        name = "ProductsOrder.findOrderAfterDate",
        query = "SELECT * FROM products_order WHERE bought_date >=:AFTER AND bought_date <=:BEFORE",
        resultClass = ProductsOrder.class
)


//@NamedNativeQuery(
//        name = "ProductsOrder.findOrderAfterDate",
//        query = "SELECT * FROM products_order WHERE bought_date >= '2018-06-01 00:00:00' and bought_date <= '2018-06-15 00:00:00'",
//        resultClass = ProductsOrder.class
//)


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductsOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private BigDecimal totalValue;

    @Column
    private int totalAmount;

    @Column
    private Date boughtDate;

    @Column
    private String linkDelivery;

    @Column
    private Date sendDate;

    @Column
    private Date deliveredDate;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(
            targetEntity = ProductBought.class,
            mappedBy = "productsOrder",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductBought> productBoughts = new ArrayList<>();

    @OneToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private Status status;

    public ProductsOrder(Users user) {
        this.boughtDate = new Date();
        this.user = user;
    }
}



