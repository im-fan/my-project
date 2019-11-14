package com.project.es.web.dao.es;

import com.project.es.web.entity.es.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ProductCustom extends ElasticsearchRepository<Product,String> {

}
