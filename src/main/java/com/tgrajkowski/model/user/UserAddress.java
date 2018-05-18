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
    private String state;

    @Column
    private String county;

    @Column
    private String city;

    @Column
    private String district;

    @Column
    private String subdistrict;

    @Column
    private String street;

    @Column
    private String postCode;

    @Column
    private int house;

    @Column
    private int apartment;

}
