package com.main.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * 导出Excel,基于alibaba/easyexcel
 *
 *@author: Weiyf
 *@Date: 2019-06-18 15:21
 */
public class ExcelUtil {

    /** 平台端导出excel
     *
     * @param results 导出的结果
     * @param fileName 文件名
     * @param clazz 结果对象
     * @param sheetName 页名
     * @param response
     * **/
    public static void exportExcel(List results,
                             String fileName,
                             Class clazz,
                             String sheetName,
                             HttpServletResponse response) throws IOException {
        exportTablesExcel(Arrays.asList(results),
                fileName,
                Arrays.asList(clazz),
                sheetName,
                response);
    }

    /**
     * 一个sheet，多个表格
     *
     * results和clazzs同下表的对象需一致
     *
     *@author: Weiyf
     *@Date: 2019-06-18 14:53
     */
    public static void exportTablesExcel(List<List> results,
                                   String fileName,
                                   List<Class> clazzs,
                                   String sheetName,
                                   HttpServletResponse response) throws IOException {
        //文件名
        fileName = fileName +
                LocalDateUtil.dateToStr(LocalDateUtil.getDateNow(),
                        LocalDateUtil.DatePattern.SHORT)+".xlsx";
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename*= UTF-8''"+ URLEncoder.encode(fileName,"UTF-8"));

        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        Sheet sheet1 = new Sheet(1,
                1,
                null,
                sheetName,
                null);
        sheet1.setAutoWidth(Boolean.TRUE);

        /** 多个表格数据 **/
        for(int i=0; i<clazzs.size(); i++){

            Table table1 = new Table(i+1);
            table1.setClazz(clazzs.get(i));
            writer.write(results.get(i), sheet1,table1);
        }

        writer.finish();
        out.flush();
    }

}
