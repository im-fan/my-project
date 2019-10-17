package com.project.web.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleDemo {

    @ApiModelProperty("ID")
    @ExcelProperty(value = "ID", index = 0)
    private Integer id;

    @ApiModelProperty("姓名")
    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @ApiModelProperty("备注")
    @ExcelProperty(value = "备注", index = 2)
    private String desc;


}
