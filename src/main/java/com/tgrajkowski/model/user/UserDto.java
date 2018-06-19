package com.tgrajkowski.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
@ToString
public class UserDto {

    private Long id;

    @Email
    private String login;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}")
    private String password;

    @NotNull
    private String address;

    @NotNull
    @Min(1)
    @Max(999)
    private int house;

    @Max(999)
    int apartment;

    private String country;
    private String state;
    private String county;
    private String city;
    private String district;
    private String postCode;
    private String street;

    public UserDto(String name, String surname, String country, String city, String postCode, String street) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
    }

}
