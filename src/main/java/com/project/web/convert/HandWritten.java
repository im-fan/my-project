package com.project.web.convert;

import com.project.util.LocalDateUtil;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 特殊操作逻辑
 * 工具自动根据入参出参引用对应方法
 *@author: Weiyf
 *@Date: 2019-05-14 13:37
 */
@Configuration
public class HandWritten {

    public String dateToStr(Date date){
        return date != null ? LocalDateUtil.dateToStr(date, LocalDateUtil.DatePattern.LONG) : null;
    }

    public String addStr(String str){
        return str + "-修改后的值";
    }

}
