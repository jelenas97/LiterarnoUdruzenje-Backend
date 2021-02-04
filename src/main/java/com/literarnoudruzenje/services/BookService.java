package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {
    Book save(Book book);
    List<Book> getAll();

    public Book findByTitle(String title) { return bookRepository.findByTitle(title); }
}
