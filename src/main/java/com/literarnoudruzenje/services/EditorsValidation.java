package com.literarnoudruzenje.services;

import com.literarnoudruzenje.exceptions.FormFieldInputException;
import com.literarnoudruzenje.model.User;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EditorsValidation implements JavaDelegate {

    private final UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Map<String, Object> formData = (Map<String, Object>) delegateExecution.getVariable("form-data");
        List<String> editors = (List<String>) formData.get("editors");

        List<User> editorsList = editors.stream().map(editorId -> userService.findById(Long.parseLong(editorId))).collect(Collectors.toList());

        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            if (entry.getValue() == null) {
                delegateExecution.setVariable("validation", false);
                throw new FormFieldInputException(String.format("You must fill all fields.", entry.getKey()));
            } else {
                if (editors.size() < 2) {
                    delegateExecution.setVariable("validation", false);
                    throw new FormFieldInputException(String.format("You must choose minimum 2 editors", entry.getKey()));
                } else {
                    delegateExecution.setVariable("validation", true);
                    delegateExecution.setVariable("editorsList", editorsList);
                }

            }
            delegateExecution.setVariable("validation", true);
        }
    }
}
