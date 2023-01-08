package com.zhang.service;

import com.zhang.entity.PageResult;
import com.zhang.entity.RequestParams;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EsSearchService {
    @Autowired
    private  RestHighLevelClient client;






}
