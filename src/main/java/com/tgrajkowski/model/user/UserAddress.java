package com.tgrajkowski.model.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserAddress {
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

    public UserAddress(String country, String city, String postCode, String street) {
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
    }
}
