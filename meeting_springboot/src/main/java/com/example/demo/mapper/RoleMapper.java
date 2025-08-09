package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select * from `role` where username=#{username}")
    Role findByUsername(String username);

    @Select("select id from `role` where username=#{username}")
    Integer findByUsername1(String username);

    @Select("select * from `role` where department=#{department}")
    List<Role> findByDepartment(String department);

    @Select("select username from role where id=#{id}")
    String findById(Integer id);

    @Select("select * from `role` where role=#{role}")
    List<Role> findByRole(String role);

    @Select("select * from `role` where role=#{role} and department=#{department}")
    List<Role> findByRoleAndDepartment(String role, String department);

    @Select("select count(*) from `role`")
    int count();

    @Select("select * from role where id=#{id}")
    Role findAllById(Integer id);

    @Select("select * from role where mailbox=#{mailbox}")
    Role findByMailbox(String mailbox);

}
