package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    public static final String COOE_SUCCESS = "200";
    public static final String COOE_AUTH_ERROR = "401";
    public static final String COOE_SYS_ERROR = "500";

    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(COOE_SUCCESS,"请求成功",null);
    }

    public static Result success(Object data){
        return new Result(COOE_SUCCESS,"请求成功",data);
    }

    public static Result error(String msg){
        return new Result(COOE_SYS_ERROR,msg,null);
    }

    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    public static Result error(){
        return new Result(COOE_SYS_ERROR,"系统错误",null);
    }
}