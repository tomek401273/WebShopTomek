package com.tgrajkowski.model.shipping;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table
@Getter
@Setter
@ToString
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String postCode;

    @Column
    private String street;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String supplier;

    @Column
    private String state;

    @Column
    private String county;

    @Column
    private String district;

    @Column
    private String subdistrict;

    @Column
    private int house;

    @Column
    private int apartment;

    public ShippingAddress(String country, String city, String postCode, String street, String name, String surname, String supplier) {
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.name = name;
        this.surname = surname;
        this.supplier = supplier;
    }
}
