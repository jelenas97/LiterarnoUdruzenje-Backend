package com.literarnoudruzenje.controller;

import com.literarnoudruzenje.dto.NoteDto;
import com.literarnoudruzenje.dto.ProcessDto;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plagiarism")
public class PlagiarismController {

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

    @GetMapping(path = "/startProcess/{username}")
    public ProcessDto startProcess(@PathVariable String username) {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Plagiarism");
        runtimeService.setVariable(pi.getId(), "piId", pi.getId());
        ProcessDto processDto = new ProcessDto();
        processDto.setProcessId(pi.getId());
        User user = userService.findByUsername(username);
        runtimeService.setVariable(pi.getId(), "processWriterEmail", user.getEmail());
        runtimeService.setVariable(pi.getId(), "processWriter", user.getUsername());
        return processDto;
    }

    @GetMapping(path = "/plagiarism/{processId}", produces = "application/json")
    public @ResponseBody
    List<NoteDto> getComments(@PathVariable String processId) {
        List<NoteDto> comments = (List<NoteDto>) runtimeService.getVariable(processId, "commentsFromBR");
        return comments;
    }
}
