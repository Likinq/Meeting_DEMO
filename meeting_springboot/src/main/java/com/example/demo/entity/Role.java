package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Role {
    @TableId(type = IdType.AUTO)
    private int id;
    private String role;
    private String username;
    private String password;
    private String mailbox;
    private String department;
    private String state;
    @TableField(exist = false)
    private String token;
}
