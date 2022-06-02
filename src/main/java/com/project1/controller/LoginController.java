package com.project1.controller;

import com.project1.model.LoginTemplate;
import com.project1.model.User;
import com.project1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginTemplate loginTemplate) {

        return ResponseEntity.ok(userService.login(loginTemplate.getUserName(), loginTemplate.getEmail()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        userService.logout();

        return ResponseEntity.accepted().build();
    }

}
