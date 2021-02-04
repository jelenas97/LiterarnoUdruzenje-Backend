package com.literarnoudruzenje.controller;

import com.literarnoudruzenje.dto.*;
import com.literarnoudruzenje.model.Book;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bookPublishing")

public class BookPublishingController {


    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    UserService userService;

    @GetMapping(path = "/startBookPublishing/{username}")
  //  @PreAuthorize("hasAnyAuthority('WRITER')")
    public @ResponseBody ResponseEntity startBookPublishingProcess(@PathVariable String username){

        if(userService.findByUsername(username).getType().equals("WRITER")){
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("BookPublishing");
            runtimeService.setVariable(pi.getId(), "piId", pi.getId());
            ProcessDto processDto = new ProcessDto();
            processDto.setProcessId(pi.getId());
            User user = userService.findByUsername(username);
            runtimeService.setVariable(pi.getId(), "processWriterEmail", user.getEmail());
            runtimeService.setVariable(pi.getId(), "processWriter", user.getUsername());
            runtimeService.setVariable(pi.getId(), "fileLength", Long.parseLong("1"));
            return ResponseEntity.ok(processDto);
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

    }

    @GetMapping(path = "/get/{processId}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto get(@PathVariable String processId) {
        Task task;
        try {
            task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
        } catch (IndexOutOfBoundsException e) {
            task = taskService.createTaskQuery().taskId(processId).singleResult();
        }
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), processId, properties);
    }

    @GetMapping(path = "/comments/{processId}", produces = "application/json")
    public @ResponseBody
    List<CommentDto> getComments(@PathVariable String processId) {
        List<CommentDto> comments = (List<CommentDto>) runtimeService.getVariable(processId, "commentsFromBR");
        return comments;
    }

    @GetMapping(path = "/lectorCorrections/{processId}", produces = "application/json")
    public @ResponseBody
    List<FormSubmissionDto> getLectorCorrections(@PathVariable String processId) {
        List<FormSubmissionDto> correctionForm = (List<FormSubmissionDto>) runtimeService.getVariable(processId, "lectorForm");
        return correctionForm;
    }

    @GetMapping(path = "/editorSuggestions/{processId}", produces = "application/json")
    public @ResponseBody
    List<FormSubmissionDto> getEditorSuggestions(@PathVariable String processId) {
        List<FormSubmissionDto> correctionForm = (List<FormSubmissionDto>) runtimeService.getVariable(processId, "finalSuggestions");
        return correctionForm;
    }

    @GetMapping(path = "/bookDetails/{processId}", produces = "application/json")
    public @ResponseBody
    BookDetailsDTO getBookDetails(@PathVariable String processId) {

        String writer = (String) runtimeService.getVariable(processId, "processWriter");
        String title = (String) runtimeService.getVariable(processId, "title");
        List<String> plagiarisms = (List<String>) runtimeService.getVariable(processId, "detectedPlagiarisms");

        BookDetailsDTO dto = new BookDetailsDTO(writer, title, plagiarisms);
        return dto;
    }

    @GetMapping(path = "/synopsis/{processId}", produces = "application/json")
    public @ResponseBody
    SynopsisDTO getSynopsis(@PathVariable String processId) {
        String writer  = (String) runtimeService.getVariable(processId, "processWriter");
        String title= (String) runtimeService.getVariable(processId, "title");
        String synopsis= (String) runtimeService.getVariable(processId, "synopsis");

        SynopsisDTO dto = new SynopsisDTO(writer, title, synopsis);
        return dto;
    }

    @GetMapping(path = "/submissionDetails/{processId}", produces = "application/json")
    public @ResponseBody
    SubmissionDetailsDTO getSubmissionDetails(@PathVariable String processId) {
        String pId =taskService.createTaskQuery().taskId(processId).singleResult().getProcessInstanceId();
        String chiefEditor = (String) runtimeService.getVariable(pId, "chiefEditor");
        String title= (String) runtimeService.getVariable(pId, "title");
        SubmissionDetailsDTO dto = new SubmissionDetailsDTO(chiefEditor, title);
        return dto;
    }

    @PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "form-data", map);
        runtimeService.setVariable(processInstanceId, "bookPublishing", dto);
        formService.submitTaskForm(taskId, map);
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
