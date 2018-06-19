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



