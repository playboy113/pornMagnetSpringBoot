package com.zhang;

import com.alibaba.fastjson.JSON;
import com.zhang.commons.PornIndexConstants;

import org.apache.http.HttpHost;
import org.apache.lucene.index.Term;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.zhang.commons.PornIndexConstants.MAPPING_TEMPLATE;

@SpringBootTest
public class pornMapTest {

    private RestHighLevelClient client;

    @Autowired
    private porndoService service;

    @Test
    void testBulkRequest() throws IOException {
        List<magnet_model> magnet_modelList = service.selectAll();

        BulkRequest request = new BulkRequest();
        for (magnet_model model:magnet_modelList){
            request.add(new IndexRequest("pornmagnet").id(String.valueOf(model.getId())).source(JSON.toJSONString(model), XContentType.JSON));
        }
        client.bulk(request,RequestOptions.DEFAULT);
    }

    @Test
    void testExists() throws IOException {
        GetIndexRequest request = new GetIndexRequest("pornmagnet");
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(isExists);

    }
    @Test
    void getSearch() throws IOException {
        GetRequest request = new GetRequest("pornmagnet", "256");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        magnet_model magnet_model = JSON.parseObject(json, magnet_model.class);
        System.out.println(magnet_model.getTitle());
    }
    @Test
    void testMatchAll() throws IOException {
        SearchRequest request = new SearchRequest("pornmagnet");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit:hits){
            String json = hit.getSourceAsString();
            magnet_model magnet_model = JSON.parseObject(json, magnet_model.class);
            String title = magnet_model.getTitle();
            System.out.println(title);
        }


    }

    @Test
    void testTermSearch() throws IOException {
        SearchRequest request = new SearchRequest("pornmagnet");
        request.source().query(QueryBuilders.termQuery("title","肉感"));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit:hits){
            String json = hit.getSourceAsString();
            magnet_model magnet_model = JSON.parseObject(json, magnet_model.class);
            String title = magnet_model.getTitle();
            System.out.println(title);
        }
    }
    @Test
    void testAggSearch() throws IOException {
        SearchRequest request = new SearchRequest("pornmagnet");
        request.source().size(0);
        request.source().aggregation(AggregationBuilders.terms("actress_agg").field("producer").size(20));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        Terms ActressTerms = aggregations.get("actress_agg");
        List<? extends Terms.Bucket> buckets = ActressTerms.getBuckets();

        for (Terms.Bucket bucket:buckets){
            String actress = bucket.getKeyAsString();
            System.out.println(actress);
        }


    }






    private void handleResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        // 4.1.总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("总条数：" + total);
        // 4.2.获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        for (SearchHit hit : hits) {
            // 4.4.获取source
            String json = hit.getSourceAsString();
            // 4.5.反序列化，非高亮的
            magnet_model magnet_model = JSON.parseObject(json, magnet_model.class);
            // 4.6.处理高亮结果
            // 1)获取高亮map
            System.out.println(magnet_model.getTitle());
        }
    }


    @Test
    void createSearch() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("pornmagnet");
        request.source(MAPPING_TEMPLATE,XContentType.JSON);
        client.indices().create(request,RequestOptions.DEFAULT);
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
