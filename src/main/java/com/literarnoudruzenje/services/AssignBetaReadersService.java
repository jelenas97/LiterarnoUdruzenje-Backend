package com.literarnoudruzenje.services;

import com.literarnoudruzenje.dto.CommentDto;
import com.literarnoudruzenje.dto.FormSubmissionDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignBetaReadersService implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> dto = (List<FormSubmissionDto>) delegateExecution.getVariable("registration");
        List<String> brList= new ArrayList();
        for(String id : dto.get(0).getFieldValues()){
            brList.add(userService.findById(Long.parseLong(id)).getUsername());

        }
        List<CommentDto> comments = new ArrayList<CommentDto>();
        delegateExecution.setVariable( "assignedBetaReaders", brList);
        delegateExecution.setVariable("commentsFromBR", comments);
    }
}
