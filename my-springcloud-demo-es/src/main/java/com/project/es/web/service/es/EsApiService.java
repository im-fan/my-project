package com.project.es.web.service.es;

import com.project.es.web.entity.Resp;

public interface EsApiService {

    /** 根据内容查询信息 **/
    Resp<String> searchByContext(String context);

}
