package com.zhang;

import com.alibaba.fastjson.JSON;

import lombok.extern.log4j.Log4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
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
import static com.zhang.commons.PornIndexConstants.porn_types;


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
        CreateIndexRequest request = new CreateIndexRequest("porn_types");
        request.source(porn_types, XContentType.JSON);
        client.indices().create(request,RequestOptions.DEFAULT);


    }

    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest req = new DeleteIndexRequest("porn_types");
        client.indices().delete(req,RequestOptions.DEFAULT);
    }
    @Test
    void deleteData(){
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
        deleteByQueryRequest.indices("porn_types");
        try {
            //3 通过deleteByQuery来发起删除请求
            BulkByScrollResponse deleteResponse=client.deleteByQuery(deleteByQueryRequest , RequestOptions.DEFAULT);
            if(deleteResponse.getDeleted() >=1){

                System.out.println("deleteData,删除成功，删除文档条数: "+deleteResponse.getDeleted()+" ,indexName："+"porn_types");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://124.71.239.157:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}
