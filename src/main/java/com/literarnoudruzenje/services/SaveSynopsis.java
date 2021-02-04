package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.model.Genre;
import com.literarnoudruzenje.model.Writer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveSynopsis implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Book book = new Book();
        String processWriterEmail = (String) delegateExecution.getVariable("processWriterEmail");
        List<FormSubmissionDto> bookPublishing = (List<FormSubmissionDto>) delegateExecution.getVariable("bookPublishing");

        for (FormSubmissionDto formField : bookPublishing) {
            if (formField.getFieldId().equals("title")) {
                book.setTitle(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("synopsis")) {
                book.setSynopsis(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("genres")) {
                book.setGenres(formField.getFieldValues().stream()
                        .map(x -> new Genre(Long.parseLong(x), null)).collect(Collectors.toList()));
            }
        }

        Writer user = (Writer) userService.findByEmail(processWriterEmail);
        book.setWriter(user);
        bookService.save(book);
    }
}
