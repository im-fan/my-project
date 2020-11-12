package com.my.oauth2.web.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoService implements UserDetailsService {

    private static Map<String,String> UserInfo = new HashMap<>();
    static {
        String password = new BCryptPasswordEncoder().encode("123456");
        UserInfo.put("user_1",password);
        UserInfo.put("user_2",password);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String pwd = UserInfo.get(s);
        //角色列表
        Collection<GrantedAuthority> authList = getAuthorities();

        //根据用户名从库中读取密码并返回 用户名、密码、是否启用、账户是否过期、凭证是否过期、是否非锁定账户、角色列表
        UserDetails userDetails = new User(s,pwd,true,true,true,true,authList);
        return userDetails;
    }

    /**
     * 获取权限角色，可改为从库中获取
     * */
    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }
}
