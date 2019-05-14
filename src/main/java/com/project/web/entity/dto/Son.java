package com.project.web.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Son {

    private Integer userId;

    private String userName;

    private Long number;

    private String address;

    private BigDecimal money;

}
