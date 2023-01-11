package com.zhang.service.Impl;

import com.alibaba.fastjson.JSON;
import com.zhang.entity.PornTypes;
import com.zhang.service.EsSearchService;
import com.zhang.service.typesService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class EsSearchImpl implements EsSearchService {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private com.zhang.service.typesService typesService;

    @Override
    public void createIndex(String indexName,String mapping_template) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);

        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);
        if (!isExists ){
            CreateIndexRequest request2 = new CreateIndexRequest(indexName);
            request2.source(mapping_template, XContentType.JSON);
            client.indices().create(request2, RequestOptions.DEFAULT);
        }else{
           System.out.println("已经存在了");
        }

    }

    @Override
    public void bulkRequest(String indexName) throws IOException {
        List<PornTypes> pornTypesList = typesService.selectAll();

        BulkRequest request = new BulkRequest();
        for (PornTypes model:pornTypesList){
            request.add(new IndexRequest(indexName).id(String.valueOf(model.getId())).source(JSON.toJSONString(model),XContentType.JSON));
        }
        client.bulk(request,RequestOptions.DEFAULT);
    }

    @Override
    public List<String> esAggIndex(String indexName, String aggName, String fileName) throws IOException {
        SearchRequest request = new SearchRequest(indexName);
        request.source().size(0);
        request.source().aggregation(AggregationBuilders.terms(aggName).field(fileName).size(20));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        Terms ActressTerms = aggregations.get(aggName);
        List<? extends Terms.Bucket> buckets = ActressTerms.getBuckets();
        List<String> aggList= new ArrayList<String>();
        for (Terms.Bucket bucket:buckets){
            String actress = bucket.getKeyAsString();
            aggList.add(actress);
        }
        return aggList;
    }
}
