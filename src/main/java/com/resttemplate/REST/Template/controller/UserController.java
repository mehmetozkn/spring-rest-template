package com.resttemplate.REST.Template.controller;
import com.resttemplate.REST.Template.dto.UserDto;
import com.resttemplate.REST.Template.model.User;
import com.resttemplate.REST.Template.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody User userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/updateUserById/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUser(id,user));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.deleteUser(id));

    }
}
