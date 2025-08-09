package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimeSlot {
    private LocalTime id;      // 时间点(格式 HH:mm:ss)
    private String text;    // 时间段(格式 HH:mm-HH:mm)
    private boolean value;  // 是否被占用
}
