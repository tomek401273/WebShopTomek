package com.tgrajkowski.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class  Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column
    private String code;
    @Column
    private String name;
    @ManyToMany(mappedBy = "roleList", fetch = FetchType.EAGER)
    private List<Users> userList = new ArrayList<>();
}
