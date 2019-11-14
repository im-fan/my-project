package com.project.web.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 复杂标题头
 *
 * |    主标题      |    第二标题     |
 * | 标题一 | 标题二 | 标题一 | 标题二 |
 *@author: Weiyf
 *@Date: 2019-10-17 09:49
 */
@Getter
@Setter
public class HeadExcelDemo {

    @ExcelProperty({"主标题", "标题一"})
    private String head1;

    @ExcelProperty({"主标题", "标题二"})
    private String head2;

    @ExcelProperty({"第二标题", "标题一"})
    private String head3;

    @ExcelProperty({"第二标题", "标题二"})
    private String head4;


}
