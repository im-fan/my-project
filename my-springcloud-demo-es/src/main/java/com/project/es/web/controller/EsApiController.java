package com.project.es.web.controller;

import com.project.es.web.entity.Resp;
import com.project.es.web.service.es.EsApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("es/api")
public class EsApiController {

    @Autowired
    private EsApiService esApiService;

    @GetMapping
    public Resp<String> searchByContext(@RequestParam("context") String context){
        if(StringUtils.isBlank(context)){
            return Resp.failed(context);
        }

        return esApiService.searchByContext(context);
    }

}
