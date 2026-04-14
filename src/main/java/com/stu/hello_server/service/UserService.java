package com.stu.hello_server.service;

import com.stu.hello_server.common.Result;
import com.stu.hello_server.dto.UserDTO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);


    Result<Object> getUserPage(Integer pageNum, Integer pageSize);
}