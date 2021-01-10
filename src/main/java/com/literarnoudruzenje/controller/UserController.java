package com.literarnoudruzenje.controller;


import com.literarnoudruzenje.dto.UserDTO;
import com.literarnoudruzenje.model.User;
import com.literarnoudruzenje.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces="application/json")
    public ResponseEntity<?> getUsers(){

        try {
            List<UserDTO> users = this.userService.findAllUsers();

            return new ResponseEntity<>(users, HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while loading users");
        }
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyAuthority('READER','BETAREADER', 'BOARDMEMBER', 'WRITER')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }
}
