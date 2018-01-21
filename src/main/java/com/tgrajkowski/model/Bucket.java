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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
