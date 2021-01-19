package com.literarnoudruzenje.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignChiefEditorService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

            List<User> userList = identityService.createUserQuery().memberOfGroup("editors").list();
            List<String> userIds = new ArrayList<>();
            for(User u : userList) {
                userIds.add(u.getId());
                System.out.println(u.getId());
            }

            if(!userList.isEmpty()) {
                delegateExecution.setVariable("chiefEditor", userIds.get(0));
            }
        }
}
