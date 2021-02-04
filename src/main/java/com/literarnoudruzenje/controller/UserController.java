package com.literarnoudruzenje.controller;

import com.literarnoudruzenje.dto.TaskDto;
import com.literarnoudruzenje.dto.UserDTO;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    FormService formService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getUsers() {

        try {
            List<UserDTO> users = userService.findAll();

            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while loading users");
        }
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyAuthority('READER','BETAREADER', 'BOARDMEMBER', 'WRITER', 'EDITOR','LECTOR')")
    public User user(Principal user) {
        return userService.findByUsername(user.getName());
    }

    @GetMapping(path = "/tasks/{username}")
    public ResponseEntity getAllTasks(@PathVariable String username) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();

        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            Map<String, Object> map= runtimeService.getVariables(processInstanceId);

            HashMap<String,String> hashMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
                hashMap.put(entry.getKey() , entry.getValue().toString());
            }

            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee(), task.getCreateTime().toString() , hashMap);
            dtos.add(t);


        }

        return new ResponseEntity(dtos, HttpStatus.OK);

    }

}
