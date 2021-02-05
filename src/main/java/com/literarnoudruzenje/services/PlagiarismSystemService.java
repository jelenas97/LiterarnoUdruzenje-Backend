package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.repository.BookRepository;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlagiarismSystemService implements JavaDelegate {

    @Autowired
    private PlagiarismMockService plagiarismMockService;

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String bookTitle = delegateExecution.getVariable("title").toString();
        Book book = bookService.findByTitle(bookTitle);
        ArrayList<String> detectedPlagiarisms;
        try {
            detectedPlagiarisms = plagiarismMockService.scanPlagiarism(book);
        } catch (Exception e) {
            throw new BpmnError("ServiceUnavailable");
        }
        delegateExecution.setVariable("detectedPlagiarisms", detectedPlagiarisms);
    }
}
