package com.literarnoudruzenje.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ReviewOpinions implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int more = (int) delegateExecution.getVariable("more");
        int accept = (int) delegateExecution.getVariable("accept");
        int decline = (int) delegateExecution.getVariable("decline");
        if(more > 0) {
            delegateExecution.setVariable("opinion", "morefiles");
            return;
        } else if(decline > accept) {
            delegateExecution.setVariable("opinion", "decline");
        } else {
            delegateExecution.setVariable("opinion", "accept");

        }
    }
}
