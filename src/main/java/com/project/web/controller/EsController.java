package com.project.web.controller;

import com.project.web.entity.es.Product;
import com.project.web.service.es.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("es")
public class EsController {


    @Autowired
    private EsService esService;

    @GetMapping("/search")
    public Page<Product> findByKey(@RequestParam("key") String key){
        return esService.search(key);
    }


}
