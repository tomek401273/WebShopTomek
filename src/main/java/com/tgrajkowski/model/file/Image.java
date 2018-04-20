package com.tgrajkowski.model.file;

import javax.persistence.*;

@Entity
@Table()
public class Image {
    @Id
    private String id;

    @Column(name = "name", columnDefinition="VARCHAR(128)")
    private String name;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    // ...
}