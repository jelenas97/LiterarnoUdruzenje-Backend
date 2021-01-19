package com.literarnoudruzenje.services;

import com.literarnoudruzenje.exceptions.FormFieldInputException;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class FilesValidation implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FileValue> values = (List<FileValue>) delegateExecution.getVariable("files");
        Long length = (Long) delegateExecution.getVariable("fileLength");

        if (values.size() < length) {
            delegateExecution.setVariable("fileUploadValid", false);
            throw new FormFieldInputException(String.format("You must import desired number of files."));
        }
        delegateExecution.setVariable("fileUploadValid", true);
    }

}
