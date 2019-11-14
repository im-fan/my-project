package com.project.web.constant;

/**
 * sql类型
 *
 *@author: Weiyf
 *@Date: 2019-07-15 14:46
 */
public enum SqlType {

    INSERT,

    UPDATE,

    DELETE,

    CREATE,

    ALTER,

    DROP,

    QUERY,

    TRUNCATE,

    RENAME,

    CINDEX,

    DINDEX;

    /** ddl操作语句开头 **/
    public static String[] DDL = new String[]{ALTER.name(),CREATE.name(), TRUNCATE.name(),DROP.name()};
}

