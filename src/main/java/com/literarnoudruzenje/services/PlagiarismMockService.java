package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.model.PublishedBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlagiarismMockService {

    public ArrayList<String> scanPlagiarism(Book book) {
        ArrayList<String> list = new ArrayList<String>();
            list.add("War and Peace, Leo Tolstoy");
            list.add("The Great Gatsby, F. Scott Fitzgerald");
            list.add("Hamlet, William Shakespeare");
            list.add("Madame Bovary, Gustave Flaubert");
        return list;
    }
}
