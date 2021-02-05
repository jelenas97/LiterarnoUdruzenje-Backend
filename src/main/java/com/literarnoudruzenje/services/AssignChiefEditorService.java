package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.User;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignChiefEditorService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

            /*List<User> userList = identityService.createUserQuery().memberOfGroup("editors").list();
            List<String> userIds = new ArrayList<>();
            for(User u : userList) {
                userIds.add(u.getId());
                System.out.println(u.getId());
            }*/

            List<User> users = userService.findByType("EDITOR");
            String username;
            try {
                username = users.get(0).getUsername();
            } catch (Exception e) {
                throw new BpmnError("NoChiefEditor");
            }
            String email = users.get(0).getEmail();

                delegateExecution.setVariable("chiefEditor", username);
                delegateExecution.setVariable("chiefEditorMail", email);
        }
}
