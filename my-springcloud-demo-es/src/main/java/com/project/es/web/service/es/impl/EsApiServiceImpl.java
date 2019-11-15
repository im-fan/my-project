package com.project.es.web.service.es.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.es.web.entity.Resp;
import com.project.es.web.entity.es.Product;
import com.project.es.web.entity.es.SearchResultPo;
import com.project.es.web.service.es.EsApiService;
import com.utils.OkhttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API方式调用es服务
 *
 *@author: Weiyf
 *@Date: 2019-11-15 14:46
 */
@Service
@Slf4j
public class EsApiServiceImpl implements EsApiService {

    @Autowired
    private RestClient client;

    @Override
    public Resp<String> searchByContext(String context) {

        Request request = new Request("POST",
                new StringBuilder("products/_search").toString());

        Response response = null;
        try {


            // 将数据丢进去，这里一定要外包一层“doc”，否则内部不能识别
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query", JSON.parseObject("{\n" +
                    "    \"simple_query_string\": {\n" +
                    "      \"query\": \""+context+"\",\n" +
                    "      \"fields\": [\"title\"]\n" +
                    "    }\n" +
                    "}"));
            request.setEntity(new NStringEntity(jsonObject.toString(),ContentType.APPLICATION_JSON));
            // 还可以将其设置为String，默认为ContentType为application/json
            //request.setJsonEntity("{\"json\":\"text\"}");

            // 添加json返回优化
            request.addParameter("pretty", "true");


            // 执行HTTP请求
            response = client.performRequest(request);

            String responseBody = EntityUtils.toString(response.getEntity());
            //解析返回信息
            List<Product> productList = convertToObject(responseBody);

            return Resp.success(productList);
        } catch (IOException e) {
            log.error("查询异常,response={},error={}",JSONObject.toJSONString(response),e);
            return Resp.failed("查询异常");
        }
    }


    /** 解析返回信息 **/
    private List<Product> convertToObject(String responseBody) throws IOException {

        JSONObject responseEntity = JSONObject.parseObject(responseBody);
        String hits = JSONObject.toJSONString(
                JSONObject.parseObject(responseEntity.get("hits").toString()).get("hits")
        );

        List<SearchResultPo> searchResultPos = JSONObject.parseArray(hits,SearchResultPo.class);

        List<Product> results = searchResultPos.stream().map(
                po -> JSONObject.parseObject(po.get_source(),Product.class)
        ).collect(Collectors.toList());

        return results;
    }

}
