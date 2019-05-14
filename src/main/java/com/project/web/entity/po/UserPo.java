package com.project.web.entity.po;

import com.project.web.entity.dto.Son;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class UserPo {

    private Long userId;

    private String userName;

    private String number;

    private String address;

    private BigDecimal money;

    private Date birthday;

    private Date marryDay;

    private List<Son> sons;

    private Map<String,String> strMaps;

    private Set<Integer> sets;

}
