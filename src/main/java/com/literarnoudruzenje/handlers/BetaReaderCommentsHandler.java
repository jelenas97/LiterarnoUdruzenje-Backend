package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.List;

public class BetaReaderCommentsHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDto> dto = (List<FormSubmissionDto>) delegateTask.getVariable("registration");
        delegateTask.setVariable( "betaReaderCommentForm", dto);
    }
}
