package com.zhang;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
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
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    //private porndoService porndoService;

//    @Test
//    void testService(){
//        System.out.println(porndoService);
//    }





    @Test
    void testExists() throws IOException {
        GetIndexRequest request = new GetIndexRequest("porn_types");
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
    void deleteIndex() throws IOException {
        DeleteIndexRequest req = new DeleteIndexRequest("porn_types");
        client.indices().delete(req,RequestOptions.DEFAULT);
    }



    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.47.129:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}
