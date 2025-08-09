package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.SimpleOrderManager;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class SimpleOrderController {
    @Resource
    SimpleOrderManager sim;

    @Resource
    RoleMapper roleMapper;

    public record MailResponse(String code, Role role) {}

    @GetMapping("/sendmail")
    public MailResponse sendMail(@RequestParam String mail) {
        Role dbrole = roleMapper.findByMailbox(mail);
        if (dbrole == null) {
            throw new ServiceException("邮箱错误");
        }
        return new MailResponse(sim.sendMail(mail), dbrole);
    }
}
