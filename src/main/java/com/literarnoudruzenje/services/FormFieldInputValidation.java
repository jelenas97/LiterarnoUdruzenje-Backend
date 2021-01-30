package com.literarnoudruzenje.services;

import com.literarnoudruzenje.exceptions.FormFieldInputException;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class FormFieldInputValidation implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Map<String, Object> formData = (Map<String, Object>) delegateExecution.getVariable("form-data");

        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            final Object value = entry.getValue();

            if (entry.getValue() == null) {
                delegateExecution.setVariable("validation", false);
                throw new FormFieldInputException(String.format("You must fill all fields.", entry.getKey()));
            }
            if (entry.getKey().equals("username")) {
                if (userService.findByUsername((String) entry.getValue()) != null) {
                    delegateExecution.setVariable("validation", false);
                    throw new FormFieldInputException(String.format("Username already exists.", entry.getKey()));
                }
            }
            if (entry.getKey().equals("email")) {
                if (userService.findByEmail((String) entry.getValue()) != null) {
                    delegateExecution.setVariable("validation", false);
                    throw new FormFieldInputException(String.format("Email already exists.", entry.getKey()));
                }
            }
            delegateExecution.setVariable("validation", true);
        }

    }
}
