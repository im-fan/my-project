package com.project.es.web.entity.es;

import lombok.Getter;
import lombok.Setter;

/**
 * es搜索返回的通用结构体
 *
 *@author: Weiyf
 *@Date: 2019-11-15 15:36
 */
@Getter
@Setter
public class SearchResultPo {

    private String _index;
    private String _type;
    private String _id;
    private String _score;
    private String _source;

}
