package com.stanionutraul.controller;


import com.stanionutraul.model.User;
import com.stanionutraul.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.insertUser(user);
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
       return userService.updateUser(id,user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }








}
