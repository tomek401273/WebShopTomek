package com.tgrajkowski.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bucket {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    private String user;


    @OneToMany
    List<Product> productList = new ArrayList<>();

    public int samPrice() {
        return 0;
    }

    public void empty() {

    }

    public void remove(Product product) {

    }

    public void add(Product product) {

    }


}
