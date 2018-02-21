package com.tgrajkowski.model;

import com.sun.javafx.geom.transform.Identity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String password;
    @Column(unique = true)
    private String login;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER )
    @JoinTable(name = "User_Roles", joinColumns = {@JoinColumn (name = "user_id")}, inverseJoinColumns={@JoinColumn(name="role_id")})
    private List<Role> roleList = new ArrayList<>();

    public User(String name, String surname, String password, String login) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
    }
}
