package com.tgrajkowski.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bucket {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;


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
