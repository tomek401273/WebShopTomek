package com.tgrajkowski.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String login;

    public UserDto(String name, String surname, String password, String login) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
    }
}
