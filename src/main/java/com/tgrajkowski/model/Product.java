package com.tgrajkowski.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Integer price;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String ImageLink;

    public Product() {
    }

    public Product(Integer price, String title, String description, String imageLink) {
        this.price = price;
        this.title = title;
        this.description = description;
        ImageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ImageLink='" + ImageLink + '\'' +
                '}';
    }
}
