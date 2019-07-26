package com.project.web.entity.po;

import com.project.web.entity.dto.Son;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserTwoPo extends UserPo {

    @Builder
    public UserTwoPo(Long userId,
                     String userName,
                     String number,
                     String address,
                     BigDecimal money,
                     Date birthday,
                     Date marryDay,
                     List<Son> sons,
                     Map<String,String> strMaps,
                     Set<Integer> sets){
        super(userId,userName,number,address,money,birthday,marryDay,sons,strMaps,sets);
    }

}
