package com.my.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class Oauth2ServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServiceApplication.class,args);
        System.out.println("API文档 http://localhost:8200/swagger-ui.html");
    }

}
