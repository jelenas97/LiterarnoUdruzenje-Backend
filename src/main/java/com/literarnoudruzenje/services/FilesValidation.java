package com.literarnoudruzenje.services;

import com.literarnoudruzenje.exceptions.FormFieldInputException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;

import java.util.List;
import java.util.Map;

public class FilesValidation implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FileValue> values = (List<FileValue>) delegateExecution.getVariable("files");

        if (values.size() < 2) {
            delegateExecution.setVariable("fileUploadValid", false);
            throw new FormFieldInputException(String.format("You must import desired number of files."));
        }
        delegateExecution.setVariable("fileUploadValid", true);
    }

}
