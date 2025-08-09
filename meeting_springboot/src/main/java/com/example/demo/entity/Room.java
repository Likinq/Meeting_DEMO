package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;

@Data
public class Room {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String roomname;
    private String location;
    private int capacity;
    private String equipment;
    private Time open;
    private Time close;
    private String state;
}
