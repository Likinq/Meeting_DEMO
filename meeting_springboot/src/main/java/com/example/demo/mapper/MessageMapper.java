package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    @Select("SELECT * FROM message WHERE toid=#{toid} ORDER BY time ASC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "fromid",property = "fromid"),
            @Result(column = "toid",property = "toid"),
            @Result(column = "content",property = "content"),
            @Result(column = "about",property = "about"),
            @Result(column = "time",property = "time"),
            @Result(column = "fromid",property = "fromname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "toid",property = "toname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Message> findByToid(int toid);

    @Select("select * from `message` where fromid=#{fromid} ORDER BY time ASC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "fromid",property = "fromid"),
            @Result(column = "toid",property = "toid"),
            @Result(column = "content",property = "content"),
            @Result(column = "about",property = "about"),
            @Result(column = "time",property = "time"),
            @Result(column = "fromid",property = "fromname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "toid",property = "toname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Message> findByFromid(int fromid);
}
