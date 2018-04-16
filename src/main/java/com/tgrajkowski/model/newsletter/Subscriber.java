package com.tgrajkowski.model.newsletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String code;

    @Column
    private Date lastNewsletterSend;

    @Column
    private boolean isConfirm;

    @Column
    private String confirmCode;

    public Subscriber(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
