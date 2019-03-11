package com.project.web.service.es.impl;

import com.project.web.entity.es.EsEntity;
import com.project.web.service.es.EsService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EsServiceImpl implements EsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JestClient jestClient;

    @Override
    public void saveEs(EsEntity esEntity) {
        Index index = new Index.Builder(esEntity)
                .index(EsEntity.INDEX_NAME)
                .type(EsEntity.TYPE)
                .build();
        try{

            jestClient.execute(index);
            log.info("保存至es操作完成");
        } catch (Exception e){
            log.info("保存至ES操作异常，msg={}",e.getMessage());
        }
    }

    @Override
    public void saveListEs(List<EsEntity> esEntityList) {
        Bulk.Builder bulk = new Bulk.Builder();
        for(EsEntity entity : esEntityList) {
            Index index = new Index.Builder(entity)
                    .index(EsEntity.INDEX_NAME)
                    .type(EsEntity.TYPE).build();
            bulk.addAction(index);
        }
        try {
            jestClient.execute(bulk.build());
            log.info("ES 插入完成");
        } catch (IOException e) {
            log.error("es插入异常,%s",e.getMessage());
        }
    }

    @Override
    public List<EsEntity> searchEsEntity(String searchContent) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.queryStringQuery(searchContent));
        //searchSourceBuilder.field("name");
        searchSourceBuilder.query(QueryBuilders.matchQuery("name",searchContent));
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(EsEntity.INDEX_NAME).addType(EsEntity.TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            return result.getSourceAsObjectList(EsEntity.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
