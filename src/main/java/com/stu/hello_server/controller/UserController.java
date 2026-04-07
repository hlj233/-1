package com.stu.hello_server.controller;

import com.stu.hello_server.common.Result;
import com.stu.hello_server.dto.UserDTO;
import com.stu.hello_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public Result<String> register(@RequestBody UserDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO dto) {
        return userService.login(dto);
    }

    @GetMapping("/{id}")
    public Result<String> get(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}