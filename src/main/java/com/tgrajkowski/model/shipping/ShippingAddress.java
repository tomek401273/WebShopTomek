package com.tgrajkowski.model.shipping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table
@Getter
@Setter
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
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
