package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("WRITER")
public class Writer extends User {

    @ManyToMany
    @JoinTable(name = "writer_genres",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;

    @OneToMany(mappedBy = "writer")
    private List<Book> books;
}
