package com.project.web.service.es;

import com.project.web.entity.es.Product;
import org.springframework.data.domain.Page;

/**
 * es相关方法
 *
 *@author: Weiyf
 *@Date: 2019-03-11 15:55
 */
public interface EsService {

    Page<Product> search(String searchContent);

}
