package com.literarnoudruzenje.controller;

import com.literarnoudruzenje.dto.FormFieldsDto;
import com.literarnoudruzenje.dto.FormSubmissionDto;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.xpath.XPath;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class TestController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Registracija_citaoca");
        runtimeService.setVariable(pi.getId(), "piId", pi.getId());

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @GetMapping(path = "/activate/{piId}", produces = "application/json")
    public @ResponseBody String activateUser(@PathVariable String piId) {

        MessageCorrelationResult ecmMessageArrived = runtimeService.createMessageCorrelation("UserActivation")
                .processInstanceId(piId)
                .correlateWithResult();
        return "";
    }


    @PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);
        runtimeService.setVariable(processInstanceId, "validation", true);

        try {
            formService.submitTaskForm(taskId, map);
        }catch (FormFieldValidatorException formFieldValidatorException) {
            runtimeService.setVariable(processInstanceId, "validation", false);
            throw new FormFieldValidationException("", formFieldValidatorException.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            if(temp.getFieldValue() == null) {
                map.put(temp.getFieldId(), temp.getFieldValues());
            } else {
                map.put(temp.getFieldId(), temp.getFieldValue());
            }
        }

        return map;
    }
}
