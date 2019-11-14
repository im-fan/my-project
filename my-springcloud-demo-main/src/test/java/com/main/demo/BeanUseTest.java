package com.main.demo;

import com.project.web.entity.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * bean使用测试方法
 *
 *@author: Weiyf
 *@Date: 2019-03-13 14:32
 */
public class BeanUseTest {

    private static void testBeanWrapper(){
        User user = new User();
        BeanWrapper beanWrapper = new BeanWrapperImpl(user);
        beanWrapper.setPropertyValue("name","User");
        System.out.println("beanWrapper====>"+beanWrapper.getPropertyDescriptor("name"));
    }

    public static void main(String[] args){
        testBeanWrapper();
    }

}
