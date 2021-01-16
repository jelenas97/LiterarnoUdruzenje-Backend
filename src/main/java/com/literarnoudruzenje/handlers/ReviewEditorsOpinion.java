package com.literarnoudruzenje.handlers;
import com.literarnoudruzenje.dto.FormSubmissionDto;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ReviewEditorsOpinion implements TaskListener {
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        HashMap<String, Object> map  = (HashMap<String, Object>) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "form-data");
        String opinion = (String) map.get("opinion");
        if(opinion.equals("more")) {
            int more = (int) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "more");
            runtimeService.setVariable(delegateTask.getProcessInstanceId(), "more", ++more);
        } else if(opinion.equals("decline")) {
            int decline = (int) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "decline");
            runtimeService.setVariable(delegateTask.getProcessInstanceId(), "decline", ++decline);
        } else if(opinion.equals("accept")) {
            int accept = (int) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "accept");
            runtimeService.setVariable(delegateTask.getProcessInstanceId(), "accept", ++accept);
        }
    }
}
