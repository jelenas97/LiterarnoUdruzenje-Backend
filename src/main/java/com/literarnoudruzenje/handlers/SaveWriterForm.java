package com.literarnoudruzenje.handlers;

import com.literarnoudruzenje.dto.FormSubmissionDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.util.List;

public class SaveWriterForm implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> dto = (List<FormSubmissionDto>) delegateExecution.getVariable("registration");
        delegateExecution.setVariable( "writerForm", dto);

    }
}
