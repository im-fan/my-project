package com.project.web.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * binlog数据传输对象
 *
 *@author: Weiyf
 *@Date: 2019-07-15 11:43
 */
@Getter
@Setter
@AllArgsConstructor
public class BinlogDto {

    /** 事件 **/
    private String event;

    /** 变动值 **/
    private Object value;

}
