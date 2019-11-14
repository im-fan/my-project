package com.project.es.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/** 返回值 **/
@Getter
@Setter
public class Resp<T> {

    public static final int Success = 200;

    @ApiModelProperty("状态码")
    private int status;

    @ApiModelProperty("返回数据")
    private T data;

    @ApiModelProperty("消息提示")
    private String message;


    public Resp(int status,T data,String message){
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resp success(T data){
        return new Resp(Success,data,null);
    }

    public static Resp success(){
        return new Resp(Success,null,"成功");
    }

}
