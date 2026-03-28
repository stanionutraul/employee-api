package com.stanionutraul.controller;


import com.stanionutraul.dto.UserRequestDTO;
import com.stanionutraul.dto.UserResponseDTO;
import com.stanionutraul.model.User;
import com.stanionutraul.service.UserService;
import jakarta.validation.Valid;
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
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserResponseDTO addUser(@RequestBody  @Valid UserRequestDTO dto) {
        return userService.insertUser(dto);
    }

    @GetMapping("{id}")
    public UserResponseDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public UserResponseDTO updateUser(@PathVariable Integer id, @RequestBody @Valid UserRequestDTO dto) {

       return userService.updateUser(id,dto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }











}
