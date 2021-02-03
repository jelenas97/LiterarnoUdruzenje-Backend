package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AssignChiefEditorService implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

            List<User> userList = userService.findAllByType("EDITOR");

            if(!userList.isEmpty()) {
                delegateExecution.setVariable("chiefEditor", userList.get(0).getUsername());
                delegateExecution.setVariable("chiefEditorEmail", userList.get(0).getEmail());
            }
        }
}
