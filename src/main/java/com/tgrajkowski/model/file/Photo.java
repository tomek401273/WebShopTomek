package com.tgrajkowski.model.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NamedNativeQuery(
        name="Photo.savePhotoBase64",
        query = "INSERT INTO photo(id, content_type, filte_byte, name) VALUES (:ID, 'jpg', lo_from_bytea(0, :BYTEA), :NAME)",
        resultClass = Photo.class
)

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String contentType;

    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte []filteByte;
}
