package com.my.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

@Configuration
public class Oauth2ServerConfig {


    private static final String DEMO_RESOURCE_ID = "user";

    @Autowired
    PasswordEncoder passwordEncoder;


    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    //.and().requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests()
//                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
                    //配置接口访问控制，必须认证过后才可以访问
                    .antMatchers("/user/**").authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{
        @Autowired
        AuthenticationManager authenticationManagerBean;
        @Autowired
        RedisConnectionFactory redisConnectionFactory;
        @Resource
        PasswordEncoder passwordEncoder;
        @Autowired
        UserDetailsService userDetailsService;


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            //配置两个客户端,一个用于password认证一个用于client认证
            clients

                    /** 方式一 用内存存储 **/
                    .inMemory()

                    .withClient("client_1")
                    .resourceIds(DEMO_RESOURCE_ID)
                    //设置验证方式,每个client都需要注册 - 认证类型 "authorization_code","client_credentials","token","refresh_token","password"
                    .authorizedGrantTypes("authorization_code","client_credentials","token","refresh_token","password")
                    //权限范围,每个client都需要设置
                    .scopes("select")
                    .authorities("client")
                    //安全码需要加密,每个client都需要设置
                    .secret(passwordEncoder.encode("123456"))
                    //注册授权收跳转的路径,每个client都需要注册
                    .redirectUris("http://www.baidu.com")

                    .and()
                    .withClient("client_2")
                    .resourceIds(DEMO_RESOURCE_ID)
                    .authorizedGrantTypes("client_credentials")
                    .scopes("select")
                    .authorities("client")
                    .secret(passwordEncoder.encode("123456"))
                    .redirectUris("http://www.baidu.com");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(new RedisTokenStore(redisConnectionFactory))
                    .authenticationManager(authenticationManagerBean)
                    // 刷新token必须要注入userDetailsService，
                    // 否则无法根据refresh_token加载用户信息，
                    // 因为参数中只传递了client的信息
                    //token存储方式-redis
                    .userDetailsService(userDetailsService)
                    //允许的请求方式
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }
    }


}

