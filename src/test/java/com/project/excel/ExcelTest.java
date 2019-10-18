package com.project.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.main.utils.ExcelUtil;
import com.project.web.entity.excel.HeadExcelDemo;
import com.project.web.entity.excel.SimpleDemo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {

    public static final String FilePath = "/Users/mac/Desktop/excel/";

    public static String fileName = FilePath + System.currentTimeMillis()+".xlsx";

    /** 简单导出方法一 **/
    @Test
    public void simpleOneExportTest(){
        ExcelUtil.simpleExportOne(buildData(),fileName,SimpleDemo.class,"表格一");
    }

    /** 简单导出方法二 **/
    @Test
    public void simpleTwoExportTest(){
        ExcelUtil.simpleExportTwo(buildData(),fileName,SimpleDemo.class,"表格一");
    }

    /** 复杂表头导出 **/
    @Test
    public void headExportTest(){
        ExcelUtil.simpleExportOne(buildHeadData(),fileName,HeadExcelDemo.class,"表格一");
    }

    /** 合并表格 **/
    @Test
    public void mergeExportTest(){
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。
        // 当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);

        EasyExcel.write(fileName, SimpleDemo.class)
                .registerWriteHandler(loopMergeStrategy)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("表格一")
                .doWrite(buildData());
    }

    /** 动态表头导出 **/
    @Test
    public void dynamicHeadExportTest(){
        EasyExcel.write(fileName)
                // 这里放入动态头
                .head(head()).sheet("模板")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(buildData());
    }

    /** 简单追加导出 **/
    @Test
    public void addSimportExportTest(){

        ExcelWriter excelWriter = EasyExcel.write(fileName,SimpleDemo.class).build();

        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();

        //写入数据
        excelWriter.write(buildData(),writeSheet);

        //追加写入数据
        excelWriter.write(buildData(),writeSheet);

        //关闭流
        excelWriter.finish();

    }

    /** 复杂表头导出
     * 内容相同的表头会自动合并起来
     *
     * ———————————————————————————————————————————————————————
     * | 名称     |               扫地机器人                   |
     * | 开售时间 | 2019-10-20 10:00:00 ~ 2019-10-30 10:00:00 |
     * |                                                     |
     * |   ID    |          名称          |       描述        |
     * ———————————————————————————————————————————————————————
     *
     * **/
    @Test
    public void addMoreHeadExportTest(){

        ExcelWriter excelWriter = EasyExcel.write(fileName).head(moreHead()).build();

        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();

        //写入数据
        excelWriter.write(buildData(),writeSheet);

        //关闭流
        excelWriter.finish();

    }

    public List<SimpleDemo> buildData(){
        List<SimpleDemo> demolist = new ArrayList<>();
        for(int i=0;i<9;i++ ){
            SimpleDemo demo = new SimpleDemo();
            demo.setId(i);
            demo.setName("aaaa"+i);
            demo.setDesc("a"+i+"aaa"+i);
            demolist.add(demo);
        }
        return demolist;
    }

    public List<HeadExcelDemo> buildHeadData(){
        List<HeadExcelDemo> demolist = new ArrayList<>();
        for(int i=0;i<9;i++ ){
            HeadExcelDemo demo = new HeadExcelDemo();
            demo.setHead1("head"+i);
            demo.setHead2("head"+i);
            demo.setHead3("head"+i);
            demo.setHead4("head"+i);
            demolist.add(demo);
        }
        return demolist;
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<>();
        List<String> head0 = new ArrayList<>();
        head0.add("字符串" + System.currentTimeMillis());
        head0.add("字符串2");
        head0.add("字符串3");

        List<String> head1 = new ArrayList<>();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<>();
        head2.add("日期" + System.currentTimeMillis());


        list.add(head0);
        list.add(head1);
        list.add(head2);


        return list;
    }

    /** 多表头 **/
    private List<List<String>> moreHead(){

        List<List<String>> list = new ArrayList<>();
        List<String> head0 = new ArrayList<>();
        List<String> head1 = new ArrayList<>();
        List<String> head2 = new ArrayList<>();
        head0.add("名称");head1.add("扫地机器人");head2.add("扫地机器人");
        head0.add("时间");head1.add("2019-10-20 10:00:00 ~ 2019-10-30 10:00:00");head2.add("2019-10-20 10:00:00 ~ 2019-10-30 10:00:00");
        head0.add("");head1.add("");head2.add("");
        head0.add("ID");head1.add("名称");head2.add("描述");

        list.add(head0);
        list.add(head1);
        list.add(head2);

        return list;
    }
}
