package com.tgrajkowski.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
@ToString
public class UserDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}")
    private String password;
    @Email
    private String login;
    private String country;
    private String city;
    private String postCode;
    private String street;

    public UserDto(String name, String surname, String password, String login) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
    }

    public UserDto(String name, String surname, String country, String city, String postCode, String street) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
    }

}
