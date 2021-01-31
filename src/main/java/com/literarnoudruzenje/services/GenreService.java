package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findByName(String name){ return genreRepository.findByName(name);}
}
