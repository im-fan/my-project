package com.project;

import com.alibaba.fastjson.JSONObject;
import com.project.web.entity.dto.Son;
import com.project.web.entity.dto.UserDto;
import com.project.web.entity.po.UserPo;
import com.project.web.convert.mapper.UserMapper;
import com.project.web.entity.po.UserTwoPo;
import com.utils.LocalDateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = StartApplication.class)
public class TestMapStruct {

    @Autowired
    public UserMapper userMapper;

    @Test
    public void convertTest(){
        UserPo userPo = buildUserPo();
        UserDto userDto = userMapper.toDto(userPo);
        System.out.println(JSONObject.toJSONString(userDto));
    }

    @Test
    public void convertSuperTest(){
        UserTwoPo userPo = buildUserTwoPo();
        UserDto userDto = userMapper.toDtoTwo(userPo);
        System.out.println(JSONObject.toJSONString(userDto));
    }

    public static UserPo buildUserPo(){
        Son son = new Son();
        son.setUserId(10000101);
        son.setUserName("Nalitong");
        son.setAddress("180.39,52.44");
        son.setMoney(new BigDecimal("9000.2001"));
        son.setNumber(18057184562L);


        UserPo userPo = new UserPo();
        userPo.setUserId(10001L);
        userPo.setUserName("近战法师");
        userPo.setNumber("18057184562");
        userPo.setAddress("180.39,52.44");
        userPo.setMoney(new BigDecimal("50.021"));
        userPo.setBirthday(LocalDateUtil.getDateNow());
        userPo.setMarryDay(LocalDateUtil.getDateNow());
        userPo.setSons(Collections.singletonList(son));
        Map<String,String> map = new HashMap<>();
        map.put("1","aaa");
        userPo.setStrMaps(map);
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(1001);
        userPo.setSets(hashSet);

        return userPo;
    }

    public static UserTwoPo buildUserTwoPo(){
        Son son = new Son();
        son.setUserId(10000101);
        son.setUserName("Nalitong");
        son.setAddress("180.39,52.44");
        son.setMoney(new BigDecimal("9000.2001"));
        son.setNumber(18057184562L);


        UserTwoPo userPo = new UserTwoPo();
        userPo.setUserId(10001L);
        userPo.setUserName("近战法师");
        userPo.setNumber("18057184562");
        userPo.setAddress("180.39,52.44");
        userPo.setMoney(new BigDecimal("50.021"));
        userPo.setBirthday(LocalDateUtil.getDateNow());
        userPo.setMarryDay(LocalDateUtil.getDateNow());
        userPo.setSons(Collections.singletonList(son));
        Map<String,String> map = new HashMap<>();
        map.put("1","aaa");
        userPo.setStrMaps(map);
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(1001);
        userPo.setSets(hashSet);

        return userPo;
    }

}
