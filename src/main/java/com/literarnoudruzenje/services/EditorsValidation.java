package com.literarnoudruzenje.services;

import com.literarnoudruzenje.exceptions.FormFieldInputException;
import com.literarnoudruzenje.model.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.List;
import java.util.Map;

public class EditorsValidation implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Map<String, Object> formData = (Map<String, Object>) delegateExecution.getVariable("form-data");
        List<User> editors = (List<User>) formData.get("editors");

        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            if (entry.getValue() == null) {
                delegateExecution.setVariable("validation", false);
                throw new FormFieldInputException(String.format("You must fill all fields.", entry.getKey()));
            } else {
                if (editors.size() < 2) {
                    delegateExecution.setVariable("validation", false);
                    throw new FormFieldInputException(String.format("You must choose minimum 2 editors", entry.getKey()));
                }

            }
            delegateExecution.setVariable("validation", true);
        }
    }
}
