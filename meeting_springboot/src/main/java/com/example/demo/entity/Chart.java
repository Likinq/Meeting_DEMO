package com.example.demo.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Chart {
    private Date date;
    private int count;

    // 全参构造函数（用于填充默认值）
    public Chart(Date date, Integer count) {
        this.date = date;
        this.count = count;
    }

    // 无参构造函数（MyBatis 反射需要）
    public Chart() {}
}
