package com.siemens.SpringBootAPI.controller;


import com.siemens.SpringBootAPI.entity.User;
import com.siemens.SpringBootAPI.models.*;
import com.siemens.SpringBootAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDetails> saveUser(@RequestBody UserRequest userRequest) throws myCustomException  {
        UserDetails newUser = userService.saveUser(userRequest);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/users")
    public List<UserDetails> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id, User u) throws myException {

        UserDetails user = userService.getUserById(id, u);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest) throws myCustomException  {

        UserDetails updateUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updateUser);

    }

}
