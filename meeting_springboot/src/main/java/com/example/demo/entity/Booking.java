package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

@Data
public class Booking {
    @TableId(type = IdType.AUTO)
    private int id;
    private int roomid;
    private Time begintime;
    private Time finishtime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    private int userid;
    private String topic;
    private String equipment;
    private String state;
    private String person;
    private int reviewerid;
    private String department;
    @TableField(exist = false)
    private String roomname;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String reviewername;
}
