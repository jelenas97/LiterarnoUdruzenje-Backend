package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByTitle(String title)  { return bookRepository.findByTitle(title); }
}
