package com.project.es.web.dao.es;

import com.project.es.web.entity.es.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductCustom extends ElasticsearchRepository<Product,String> {

}
