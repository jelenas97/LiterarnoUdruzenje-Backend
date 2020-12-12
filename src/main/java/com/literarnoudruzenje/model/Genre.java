package com.literarnoudruzenje.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
