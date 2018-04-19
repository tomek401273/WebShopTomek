package com.tgrajkowski.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @Lob
    @Column
    @Type(type="org.hibernate.type.BinaryType")
    @Basic(fetch = FetchType.LAZY)
    private byte []filteByte;
}
