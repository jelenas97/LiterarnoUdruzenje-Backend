package com.literarnoudruzenje.controller;

import com.literarnoudruzenje.dto.ProcessDto;
import com.literarnoudruzenje.dto.TaskDto;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
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

    @GetMapping(path = "/tasks/{username}")
    public ResponseEntity getAllTasks(@PathVariable String username){
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();

        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee(), task.getCreateTime().toString());
            dtos.add(t);
        }

        return new ResponseEntity(dtos,  HttpStatus.OK);

    }

}
