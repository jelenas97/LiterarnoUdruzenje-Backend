package com.literarnoudruzenje.services;

import com.literarnoudruzenje.exceptions.FormFieldInputException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookValidation implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Map<String, Object> formData = (Map<String, Object>) delegateExecution.getVariable("form-data");
        Object originalBook = formData.get("originalBook");
        Object plagiarismBook = formData.get("plagiarismBook");

        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            if (entry.getValue() == null) {
                delegateExecution.setVariable("validation", false);
                throw new FormFieldInputException(String.format("You must fill all fields.", entry.getKey()));
            } else {
                if (originalBook.toString().equals(plagiarismBook.toString())) {
                    delegateExecution.setVariable("validation", false);
                    throw new FormFieldInputException(String.format("You have chosen same books", entry.getKey()));
                }
            }
            delegateExecution.setVariable("validation", true);
        }

    }
}
