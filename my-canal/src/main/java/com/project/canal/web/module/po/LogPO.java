package com.project.canal.web.module.po;


import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *  日志对象
 * @Date 2020/10/14 14:14
 * @Author weiyf
**/
@Getter
@Setter
public class LogPO {
    // 记录ID
    private Long logId;
    // 表名
    private String tableName;
    //操作类型
    private CanalEntry.EventType businessType;
    //修改记录
    private List<TableInfo> infos;

    @Getter
    @Setter
    public static class TableInfo{
        //列名
        private String columnName;
        //值
        private String columnValue;
    }
}
