package com.zhang.service.Impl;

import com.alibaba.fastjson.JSON;

import com.zhang.entity.PornTypes;
import com.zhang.service.EsSearchService;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.DeleteAliasRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.zhang.commons.PornIndexConstants.MAPPING_TEMPLATE;
import static com.zhang.commons.PornIndexConstants.porn_types;


@Service("EsSearchService")
@Slf4j
public class EsSearchImpl implements EsSearchService {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private com.zhang.service.typesService typesService;

    @Override
    public void createIndex(String indexName) throws IOException {

            CreateIndexRequest request2 = new CreateIndexRequest(indexName);
            if (indexName.equals("porn_types")){
                request2.source(porn_types, XContentType.JSON);
            }else{
                request2.source(MAPPING_TEMPLATE, XContentType.JSON);

            }

        client.indices().create(request2, RequestOptions.DEFAULT);


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
        //1。先看一下这个库存不存在
//        if (testExist(indexName)){
//
//            //存在则先删除
//            deleteIndex(indexName);
//
//        }

        try{
            //不存在就直接新建
            if (!testExist(indexName)){
                createIndex(indexName);


            }
            //清空索引
            deleteAllData(indexName);

            //新建后批量导入
            bulkRequest(indexName);
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }



        SearchRequest request = new SearchRequest(indexName);
        request.source().size(0);
        request.source().aggregation(AggregationBuilders.terms(aggName).field(fileName).size(240));
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

    @Override
    public void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        client.indices().delete(request,RequestOptions.DEFAULT);
    }

    @Override
    public boolean testExist(String indexName) throws IOException {
        // 1.准备Request
        GetIndexRequest request = new GetIndexRequest(indexName);
        // 3.发送请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);


        return exists;
    }

    @Override
    public void deleteAllData(String indexName) throws IOException {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
        deleteByQueryRequest.indices(indexName);

        client.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);
    }
}
