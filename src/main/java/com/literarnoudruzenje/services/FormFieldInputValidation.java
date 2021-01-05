package com.literarnoudruzenje.services;

import com.literarnoudruzenje.exceptions.FormFieldInputException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Map;

public class FormFieldInputValidation implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Map<String, Object> formData = (Map<String, Object>) delegateExecution.getVariable("form-data");

        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            final Object value = entry.getValue();
            if (entry.getValue() == null) {
                delegateExecution.setVariable("validation", false);
                throw new FormFieldInputException(String.format("You must fill all fields.", entry.getKey()));
            }
            delegateExecution.setVariable("validation", true);
        }

    }
}
