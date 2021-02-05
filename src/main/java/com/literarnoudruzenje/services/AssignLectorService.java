package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.Lector;
import com.literarnoudruzenje.model.User;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignLectorService implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<User> lectors = userService.findByType("LECTOR");
        String username;
        try {
            username = lectors.get(0).getUsername();
        } catch(Exception e) {
            throw new BpmnError("NoLector");
        }
        String email = lectors.get(0).getEmail();

        delegateExecution.setVariable("lector", username);
        delegateExecution.setVariable("lectorMail", email);
    }
}
