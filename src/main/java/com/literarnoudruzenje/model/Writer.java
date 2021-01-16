package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("WRITER")
public class Writer extends User {

    @ManyToMany
    private List<Genre> genres;
}
