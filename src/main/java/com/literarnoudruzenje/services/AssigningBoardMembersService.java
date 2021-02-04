package com.literarnoudruzenje.services;

import com.literarnoudruzenje.model.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssigningBoardMembersService implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> boardMembers = userService.findByType("BOARDMEMBER");
        List<String> finalDecisions = new ArrayList<>();
        delegateExecution.setVariable("boardMembers", boardMembers);
        delegateExecution.setVariable("finalDecisions", finalDecisions);
        delegateExecution.setVariable("accept", 0);
        delegateExecution.setVariable("more", 0);
        delegateExecution.setVariable("decline", 0);
    }
}
