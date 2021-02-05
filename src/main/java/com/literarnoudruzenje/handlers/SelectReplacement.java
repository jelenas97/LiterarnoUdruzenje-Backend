package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SelectReplacement implements TaskListener {

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> formData = (Map<String, Object>) delegateTask.getExecution().getVariable("form-data");
        String editor = (String) formData.get("oneEditor");

        User chosenEditor = userService.findById(Long.parseLong(editor));
        delegateTask.getExecution().setVariable("editor", chosenEditor);

    }
}
