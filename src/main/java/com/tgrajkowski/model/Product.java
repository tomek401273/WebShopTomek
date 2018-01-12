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
    @GeneratedValue
    @NotNull
    private Long id;
    @Column
    private Integer price;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String ImageLink;
}
