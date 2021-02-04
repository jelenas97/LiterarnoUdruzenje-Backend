package com.literarnoudruzenje.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewVotesForPlagiarismService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> finalDecisions = (List<String>) delegateExecution.getVariable("finalDecisions");

        if (finalDecisions.stream().distinct().count() <= 1) {
            delegateExecution.setVariable("unanimous", true);
        } else {
            delegateExecution.setVariable("unanimous", false);
        }
    }
}
