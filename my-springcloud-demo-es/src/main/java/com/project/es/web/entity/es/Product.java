package com.project.es.web.entity.es;

import lombok.Getter;
import lombok.Setter;

/**
 * es model
 *
 *@author: Weiyf
 *@Date: 2019-11-13 14:56
 */
@Getter
@Setter
//@Document(indexName = "products",type = "docs", shards = 1,replicas = 0, refreshInterval = "-1")
public class Product {

//    @Id
    private String id;

//    @Field
    private String title;

//    @Field
    private String productDesc;

}
