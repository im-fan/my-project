package com.project.web.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private Integer userId;

    private String userName;

    private Long number;

    private String address;

    private BigDecimal money;

    private Date today;

    private String birthday;

    private String marryDay;

    private List<Son> sons;

    private Map<String,String> strMaps;

    private Set<Integer> sets;
}
