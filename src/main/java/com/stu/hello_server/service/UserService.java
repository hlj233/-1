package com.stu.hello_server.service;

import com.stu.hello_server.common.Result;
import com.stu.hello_server.dto.UserDTO;
import com.stu.hello_server.entity.UserInfo;
import com.stu.hello_server.vo.UserDetailVO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);
    Result<Object> getUserPage(Integer pageNum, Integer pageSize);

    // 新增：多表联查+Redis
    Result<UserDetailVO> getUserDetail(Long userId);
    Result<String> updateUserInfo(UserInfo userInfo);
    Result<String> deleteUser(Long userId);
}