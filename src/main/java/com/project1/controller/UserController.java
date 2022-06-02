package com.project1.controller;


import com.project1.dao.UserDAO;
import com.project1.model.User;
import com.project1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired()
    UserService userService;
    @Autowired()
    UserDAO userDAO;
    @Autowired()
    User user;
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user){
        boolean result = userService.addUser(user);
        ResponseEntity responseEntity = null;
        if(result){
            responseEntity = new ResponseEntity<String>
                    ("User "+user.getUserName()+" added successfully", HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>
                    ("Cannot add user, please try again", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @GetMapping("/mail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email){
        List<User> result = new ArrayList<>();
        ResponseEntity responseEntity = null;
        if(userService.getUserByEmail(email).size()>0){
            result = userService.getUserByEmail(email);
            responseEntity = new ResponseEntity<String>("Found user "+result.toString(), HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>
                    ("Cannot find user, please try again" + userService.getUserByEmail(email).size(), HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @PutMapping("/update/{Id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("Id") int Id){
        boolean result = userService.updateUser(user, Id);
        ResponseEntity responseEntity = null;
        if(result){
            responseEntity = new ResponseEntity<String>
                    ("User "+user.getUserName()+" updated successfully", HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>
                    ("Cannot update user, please try again", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<String> deleteUser(@PathVariable("Id") int Id){
        boolean result = userService.deleteUser(Id);
        ResponseEntity responseEntity = null;
        if(result){
            responseEntity = new ResponseEntity<String>
                    ("User deleted", HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>
                    ("Could not delete user", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

}
