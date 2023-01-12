package com.example.catsanddogstelegram.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_seq")
    @SequenceGenerator(name = "avatar_seq", sequenceName = "avatar_seq", allocationSize = 1)
    @Column(name = "avatar_id")
    private Integer id;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;
    @OneToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "animal_id")
    private Animal animal;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return Objects.equals(id, avatar.id);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, animal);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", student=" + animal +
                '}';
    }
}
