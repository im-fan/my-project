package com.project.web.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private int id;
    private String name;
    private int age;
    private String desc;
    private String address;
    private String createTime;



}
