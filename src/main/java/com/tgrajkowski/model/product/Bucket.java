package com.tgrajkowski.model.product;

import com.tgrajkowski.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Bucket {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(
            targetEntity = ProductAmount.class,
            mappedBy = "bucket",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<ProductAmount> productAmount = new ArrayList<>();
}
