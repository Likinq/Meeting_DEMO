package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo.common.Result;
import com.example.demo.entity.Role;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*")
public class RoleController {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleService roleService;

    @GetMapping("/findid")
    public Integer findid(@RequestParam String toname) {return roleMapper.findByUsername1(toname);}

    @GetMapping("/list")
    public List<Role> selectAll() {return roleMapper.selectList(null);}

    @GetMapping("/rolist")
    public List<Role> selectByRole(@RequestParam String role) {return roleMapper.findByRole(role);}

    @GetMapping("/delist")
    public List<Role> selectByDepartment(@RequestParam String department) {return roleMapper.findByDepartment(department);}

    @GetMapping("/rodelist")
    public List<Role> selectByRoDe(@RequestParam String role,@RequestParam String department) {return roleMapper.findByRoleAndDepartment(role,department);}

    @PostMapping("/update")
    public void update(@RequestBody Role role) {roleMapper.updateById(role);}

    @GetMapping("/delete")
    public void delete(@RequestParam Integer id) {roleMapper.deleteById(id);}

    @PostMapping("/login")
    public Result login(@RequestBody Role role) {
        if (StrUtil.isBlank(role.getUsername())|| StrUtil.isBlank(role.getPassword())) {
            return Result.error("数据输入不合法");
        }
        role = roleService.login(role);
        return Result.success(role);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role) {
        try{
            roleService.insert(role);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                return Result.error("插入数据库错误");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success(role);
    }

    @GetMapping("/count")
    public int count() {return roleMapper.count();}
}
