package com.stu.hello_server.controller;

import com.stu.hello_server.common.Result;
import com.stu.hello_server.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 1. 获取用户信息（查）
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        String data = "查询成功，正在返回 ID 为 " + id + " 的用户信息";
        return Result.success(data);
    }

    // 2. 新增用户（增）- 接收 JSON 格式数据
    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        String data = "新增成功，接收到用户：" + user.getName() + "，年龄：" + user.getAge();
        return Result.success(data);
    }

    // 3. 全量更新用户信息（改）
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        String data = "更新成功，ID " + id + " 的用户已修改为：" + user.getName();
        return Result.success(data);
    }

    // 4. 删除用户（删）
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        String data = "删除成功，已移除 ID 为 " + id + " 的用户";
        return Result.success(data);
    }
}