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
public class AssigningBoardMembersService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> userList = identityService.createUserQuery().memberOfGroup("boardMembers").list();
        List<String> userIds = new ArrayList<>();
        for(User u : userList) {
            userIds.add(u.getId());
            System.out.println(u.getId());
        }
        delegateExecution.setVariable("boardMembersList", userIds);
        delegateExecution.setVariable("accept", 0);
        delegateExecution.setVariable("more", 0);
        delegateExecution.setVariable("decline", 0);
    }
}
