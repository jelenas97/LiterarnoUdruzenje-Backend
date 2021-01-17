package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("READER")
public class Reader extends User {

    @ManyToMany
    @JoinTable(name = "reader_genres",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;
}
