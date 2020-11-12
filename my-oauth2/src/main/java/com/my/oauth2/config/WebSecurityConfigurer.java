package com.my.oauth2.config;

import com.my.oauth2.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 重写authenticationManagerBean方法
 * @Date 2020/11/9 23:08
 * @Author weiyf
**/
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    UserInfoService userInfoService;
    /**
     * 设置用户信息 第一种写法
     **/
    /*@Bean
    @Override
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
        return manager;
    }*/


    /**
     * 设置用户信息 第二中写法
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码模式下，用户的密码也需要加密
        //String password = passwordEncoder().encode("123456");

        auth
                /** 方式一 在数据库中获取用户密码 **/
                .userDetailsService(userInfoService);

                /** 方式二 配置方式  **/
                /*.inMemoryAuthentication()
                //密码加密方式-加了不生效，要直接加密好放到password中
                //.passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user_1")
                .password(password)
                .authorities("USER")
                .and()
                .withUser("user_2")
                .password(password)
                .authorities("USER");*/
    }

    /**
     * 配置开启认证相关接口
     * 注意：声明的顺序，必须先声明范围小的，再声明范围大的。
   **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭跨域保护
                .csrf().disable()
                .authorizeRequests()
                //设置请求不鉴权
                .antMatchers("/oauth/*").permitAll()
                //设置请求接口鉴权
                .antMatchers("/user/**").authenticated()
                //所有接口鉴权
                .anyRequest().authenticated()
                //basic提交，否则授权码模式无法访问授权页面
                .and().httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui",
                        "/swagger-resources","/swagger-resources/configuration/security",
                        "/swagger-ui.html","/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index"
                );
    }

    /**
     * 用第二种方法设置用户信息时，需要为UserDetailsService添加一个bean，否则启动会提示找不到userDetailsService
     **/
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码加密方式
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
