package com.zhang;

import com.alibaba.fastjson.JSON;
import com.zhang.entity.magnet_model;


import com.zhang.service.porndoService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.zhang.commons.PornIndexConstants.MAPPING_TEMPLATE;


@SpringBootTest
public class PornIndexTest {
    private RestHighLevelClient client;
    @Autowired
    private porndoService porndoService;

    @Test
    void testService(){
        System.out.println(porndoService);
    }





    @Test
    void testExists() throws IOException {
        GetIndexRequest request = new GetIndexRequest("pornmagnet");
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(isExists);

    }
    @Test
    void  createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("pornmagnet");
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        client.indices().create(request,RequestOptions.DEFAULT);


    }
    @Test
    void testBulkRequest() throws IOException {
        List<magnet_model> magnet_modelList = porndoService.selectAll();

        BulkRequest request = new BulkRequest();
        for (magnet_model model:magnet_modelList){
            request.add(new IndexRequest("pornmagnet").id(String.valueOf(model.getId())).source(JSON.toJSONString(model),XContentType.JSON));
        }
        client.bulk(request,RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.47.128:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}
