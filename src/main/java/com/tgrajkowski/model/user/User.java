package com.tgrajkowski.model.user;

import com.tgrajkowski.model.product.mark.ProductMark;
import com.tgrajkowski.model.product.order.ProductsOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String password;

    @Column(unique = true)
    private String login;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "User_Roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roleList = new ArrayList<>();

    @OneToMany(
            targetEntity = ProductsOrder.class,
            mappedBy = "user",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductsOrder> orders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private UserAddress userAddress;

    @OneToMany(
            targetEntity = ProductMark.class,
            mappedBy = "user",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductMark> productMarks = new ArrayList<>();

    public User(String name, String surname, String password, String login) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
    }
}
