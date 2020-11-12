package com.my.oauth2.web.model;


import lombok.*;

/**
 * 用户对象
 * @Date 2020/11/8 16:53
 * @Author weiyf
**/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Integer userId;

    private String userName;

    private Integer age;

    private String address;

}
