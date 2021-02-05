package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.PublishedBook;
import com.literarnoudruzenje.repository.PublishedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublishedBookService {

    @Autowired
    PublishedBookRepository publishedBookRepository;


    public List<PublishedBook> getAll() {
        return publishedBookRepository.findAll();
    }

    public void save(PublishedBook publishedBook) {
        publishedBookRepository.save(publishedBook);
    }

    public PublishedBook getById(Long id) {
        return this.publishedBookRepository.getOne(id);
    }
}
