package com.project.web.service.es;

import com.project.web.entity.es.EsEntity;

import java.util.List;

/**
 * es相关方法
 *
 *@author: Weiyf
 *@Date: 2019-03-11 15:55
 */
public interface EsService {

    String saveEs(EsEntity esEntity);

    String saveListEs(List<EsEntity> esEntityList);

    List<EsEntity> searchEsEntity(String searchContent);

}
