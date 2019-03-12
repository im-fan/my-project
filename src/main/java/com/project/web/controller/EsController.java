package com.project.web.controller;

import com.project.web.entity.es.EsEntity;
import com.project.web.service.es.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("es")
public class EsController {


    @Autowired
    private EsService esService;

    @PostMapping
    public String saveEs(@RequestBody EsEntity esEntity){
        return esService.saveEs(esEntity);
    }

    @PostMapping("list")
    public String saveEslist(@RequestBody List<EsEntity> esEntityList){
        return esService.saveListEs(esEntityList);
    }

    @GetMapping("{key}")
    public List<EsEntity> findByKey(@PathVariable String key){
        return esService.searchEsEntity(key);
    }


}
