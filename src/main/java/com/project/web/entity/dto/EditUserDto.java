package com.project.web.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserDto {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("desc")
    private String desc;

}
