package com.tgrajkowski.model.product.order;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.shipping.ShippingAddress;
import com.tgrajkowski.model.user.User;
import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int totalValue;

    @Column
    private int totalAmount;

    @Column
    private Date boughtDate;

    @Column
    private boolean isPaid;

    @Column
    private boolean isPrepared;

    @Column
    private boolean isSend;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

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
    private ShippingAddress shippingAddress;

    public ProductsOrder(int totalValue, boolean isPaid, boolean isPrepared, boolean isSend) {
        this.totalValue = totalValue;
        this.boughtDate = new Date();
        this.isPaid = isPaid;
        this.isPrepared = isPrepared;
        this.isSend = isSend;
    }

    public ProductsOrder( User user) {
        this.boughtDate = new Date();
        this.user = user;
    }
}
