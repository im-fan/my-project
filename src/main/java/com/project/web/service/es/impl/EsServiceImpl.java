package com.project.web.service.es.impl;

import com.project.web.dao.es.ProductCustom;
import com.project.web.entity.es.Product;
import com.project.web.service.es.EsService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * es demo
 *
 *@author: Weiyf
 *@Date: 2019-11-13 14:56
 */
@Service
public class EsServiceImpl implements EsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductCustom repository;

    @Override
    public Page<Product> search(String searchContent){

        SearchQuery queryBuilder = getEntitySearchQuery(1,10,searchContent);
        Page<Product> goodsPage = repository.search(queryBuilder);
        return goodsPage;

    }

    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        QueryBuilders.matchPhraseQuery("title", searchContent);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("title", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .add(QueryBuilders.matchPhraseQuery("productDesc", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                //设置权重分 求和模式
                .scoreMode("sum")
                //设置权重分最低分
                .setMinScore(10);

        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }


}
