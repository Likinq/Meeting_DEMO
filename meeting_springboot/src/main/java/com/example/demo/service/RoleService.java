package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.utils.TokenUtils;
import com.example.demo.exception.ServiceException;
import jakarta.annotation.Resource;
import com.example.demo.mapper.RoleMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
public class RoleService {
    @Resource
    RoleMapper roleMapper;

    public Role login(Role role) {
        Role dbstudent = roleMapper.findByUsername(role.getUsername());
        if (dbstudent == null) {
            throw new ServiceException("账号或密码错误");
        }
        if (!Objects.equals(role.getPassword(), dbstudent.getPassword())) {
            throw new ServiceException("账号或密码错误");
        }
        String token = TokenUtils.genToken(String.valueOf(dbstudent.getId()),dbstudent.getPassword());
        dbstudent.setToken(token);
        return dbstudent;
    }

    @Transactional
    public void insert(Role role) {
        Role dbstudent = roleMapper.findByUsername(role.getUsername());
        if (dbstudent != null) {
            throw new ServiceException("用户名已存在");
        }
        roleMapper.insert(role);
    }


}
