package com.tgrajkowski.model.product.comment;

import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.user.Users;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column
    private Date created;

    @Column
    private String message;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    public Comment(Users user, String message) {
        this.user = user;
        this.created = new Date();
        this.message = message;
    }
}
