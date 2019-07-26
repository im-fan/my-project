package com.project.web.convert.mapper;

import com.main.utils.LocalDateUtil;
import com.project.web.entity.dto.UserDto;
import com.project.web.entity.po.UserPo;
import com.project.web.convert.HandWritten;
import com.project.web.entity.po.UserTwoPo;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(
        /** Spring方式配置 **/
        componentModel = "spring",
        uses = {HandWritten.class},
        imports = {LocalDateUtil.class}
)
public interface UserMapper {

    /** 默认配置 **/
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "today",expression = "java(LocalDateUtil.getDateNow())"),
            @Mapping(source = "money",target = "money",numberFormat = "#.00"),
//            @Mapping(source = "birthday",target = "birthday",dateFormat = "yyyy-MM-dd HH:mm:ss"),
//            @Mapping(source = "marryDay",target = "marryDay",dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    UserDto toDto(UserPo userPo);

    /** 继承类转换 **/
    UserDto toDtoTwo(UserTwoPo po);

    @InheritConfiguration(name = "toDto")
    List<UserDto> toDots(List<UserPo> userPos);

}
