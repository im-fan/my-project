package com.project.web.dao.es;

import com.project.web.entity.es.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ProductCustom extends ElasticsearchRepository<Product,String> {

}
