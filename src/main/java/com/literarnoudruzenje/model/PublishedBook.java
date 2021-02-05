package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class PublishedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String work;

    @Column
    private boolean plagiarism;

    @ManyToMany
    @JoinTable(name = "published_book_genres",
            joinColumns = @JoinColumn(name = "published_book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;

    @ManyToOne
    @JoinColumn(name="writer_id", nullable=false)
    private Writer writer;
}
