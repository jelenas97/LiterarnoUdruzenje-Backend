package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.PublishedBook;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetPlagiarism implements JavaDelegate {
    @Autowired
    PublishedBookService publishedBookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String plagiarismId = (String) delegateExecution.getVariable("plagiarismBook");
        try {
            PublishedBook plagiarismBook = publishedBookService.getById(Long.parseLong(plagiarismId));
            plagiarismBook.setPlagiarism(true);
            publishedBookService.save(plagiarismBook);
        } catch (Exception e) {
            throw new BpmnError("FlagFailed");
        }
    }
}
