package com.main.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.List;

/**
 * 导出Excel,基于alibaba/easyexcel
 *
 *@author: Weiyf
 *@Date: 2019-06-18 15:21
 */
public class ExcelUtil {

    /**
     * 简单导出
     *
     *@author: Weiyf
     *@Date: 2019-10-16 17:40
     */
    public static void simpleExportOne(List results,
                                   String fileName,
                                   Class clazz,
                                   String sheetName) {

        // 写法1
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, clazz)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet(sheetName)
                .doWrite(results);


    }

    public static void simpleExportTwo(List results,
                                        String fileName,
                                        Class clazz,
                                        String sheetName) {
        // 写法2
        // 这里 需要指定写用哪个class去读
        ExcelWriter excelWriter = EasyExcel.write(fileName, clazz).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(results, writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

}
