package com.literarnoudruzenje.controller;

import com.literarnoudruzenje.dto.TaskDto;
import com.literarnoudruzenje.dto.UserDTO;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @PreAuthorize("hasAnyAuthority('READER','BETAREADER', 'BOARDMEMBER', 'WRITER', 'EDITOR')")
    public User user(Principal user) {
        return userService.findByUsername(user.getName());
    }

    @GetMapping(path = "/tasks/{username}")
    public ResponseEntity getAllTasks(@PathVariable String username) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();

        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee(), task.getCreateTime().toString());
            dtos.add(t);
        }

        return new ResponseEntity(dtos, HttpStatus.OK);

    }

}
