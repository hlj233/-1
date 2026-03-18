package com.stu.hello_server.entity;

public class User {
    private Long id;
    private String name;
    private Integer age;

    // 只保留全参构造方法
    public User() {
    }

    // Getter和Setter方法（必须保留）
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}