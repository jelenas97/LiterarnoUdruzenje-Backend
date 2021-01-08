package com.literarnoudruzenje.controller;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import com.literarnoudruzenje.dto.FormFieldsDto;
import com.literarnoudruzenje.dto.FormSubmissionDto;
import com.literarnoudruzenje.dto.ProcessDto;
import org.apache.commons.compress.utils.IOUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.xpath.XPath;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
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

    @GetMapping(path = "/startReader")
    public ProcessDto startReaderProcess(){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Writer_registration");
        runtimeService.setVariable(pi.getId(), "piId", pi.getId());
        ProcessDto processDto = new ProcessDto();
        processDto.setProcessId(pi.getId());
        return processDto;
    }

    @GetMapping(path = "/get/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDto get(@PathVariable String processId) {

        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), processId, properties);
    }

    @GetMapping(path = "/activate/{piId}", produces = "application/json")
    public @ResponseBody ResponseEntity activateUser(@PathVariable String piId) {

        try {
            MessageCorrelationResult ecmMessageArrived = runtimeService.createMessageCorrelation("UserActivation")
                    .processInstanceId(piId)
                    .correlateWithResult();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User already activated his account");
        }
    }


    @PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "form-data", map);
        runtimeService.setVariable(processInstanceId, "registration", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/uploadFile/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestParam("files") MultipartFile[] files, @PathVariable String taskId) throws IOException {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        List<FileValue> values = new ArrayList<FileValue>();
        for(MultipartFile file : files) {
            FileValue typedFileValue = Variables
                    .fileValue(file.getOriginalFilename())
                    .file(file.getBytes())
                    .mimeType("application/pdf")
                    .encoding("UTF-8")
                    .create();
            values.add(typedFileValue);
        }
        runtimeService.setVariable(processInstanceId, "files", values);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(path = "/download/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable String fileName,@RequestParam("taskId") String taskId) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        List<FileValue> values = (List<FileValue>) runtimeService.getVariable(taskId,"files");
        InputStream is = values.get(0).getValue();
        File targetFile = new File(values.get(0).getFilename());

        java.nio.file.Files.copy(
                is,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        byte[] bytes = FileUtils.readFileToByteArray(targetFile);


        IOUtils.closeQuietly(is);

        ByteArrayResource resource = new ByteArrayResource(bytes);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(targetFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
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
