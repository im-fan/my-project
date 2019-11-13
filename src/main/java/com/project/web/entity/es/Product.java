package com.project.web.entity.es;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * es model
 *
 *@author: Weiyf
 *@Date: 2019-11-13 14:56
 */
@Getter
@Setter
@Document(indexName = "products",type = "docs", shards = 1,replicas = 0, refreshInterval = "-1")
public class Product {

    @Id
    private String id;

    @Field
    private String title;

    @Field
    private String productDesc;

}
