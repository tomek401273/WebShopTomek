package com.tgrajkowski.model.location.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String name;

    @Column
    private String alpha2Code;

    public Country(String name, String alpha2Code) {
        this.name = name;
        this.alpha2Code = alpha2Code;
    }
}
