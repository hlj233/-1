package com.stu.hello_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.hello_server.common.Result;
import com.stu.hello_server.common.ResultCode;
import com.stu.hello_server.dto.UserDTO;
import com.stu.hello_server.entity.User;
import com.stu.hello_server.mapper.UserMapper;
import com.stu.hello_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        if (userMapper.selectOne(wrapper) != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user == null) return Result.error(ResultCode.USER_NOT_EXIST);
        if (!user.getPassword().equals(dto.getPassword())) return Result.error(ResultCode.PASSWORD_ERROR);
        String token = "Bearer " + UUID.randomUUID().toString().replace("-", "");
        return Result.success(token);
    }

    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) return Result.error(ResultCode.USER_NOT_EXIST);
        return Result.success("ID:" + id + " 用户名:" + user.getUsername());
    }

    // ====================== 任务6：分页实现 ======================
    @Override
    public Result<Object> getUserPage(Integer pageNum, Integer pageSize) {
        // 1.创建分页对象
        Page<User> pageParam = new Page<>(pageNum, pageSize);
        // 2.执行分页查询
        Page<User> resultPage = userMapper.selectPage(pageParam, null);
        // 3.返回分页结果（包含列表、总数、页数）
        return Result.success(resultPage);
    }
}