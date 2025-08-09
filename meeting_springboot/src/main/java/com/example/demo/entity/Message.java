package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Message {
    private int id;
    private int fromid;
    private int toid;
    private String content;
    private String about;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    @TableField(exist=false)
    private String fromname;
    @TableField(exist=false)
    private String toname;
}
