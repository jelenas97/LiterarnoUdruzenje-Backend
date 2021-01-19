package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

}
