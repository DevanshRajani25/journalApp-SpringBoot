package com.devansh.journal_app.controller;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devansh.journal_app.entity.UserEntity;
import com.devansh.journal_app.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody UserEntity user){
        userService.saveUser(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity newuser, @PathVariable String userName){
        UserEntity userinDB = userService.findByUserName(userName);
        if(userinDB!=null){
            userinDB.setUserName(newuser.getUserName());
            userinDB.setPassword(newuser.getPassword());
            userinDB.setUpdated_time(LocalDateTime.now());
            userService.saveUser(userinDB);
            return new ResponseEntity<>(newuser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
