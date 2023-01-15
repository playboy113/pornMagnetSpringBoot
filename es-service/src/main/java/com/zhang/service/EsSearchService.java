package com.zhang.service;

import java.io.IOException;
import java.util.List;

public interface EsSearchService {
    void createIndex(String indexName) throws IOException;

    void bulkRequest(String indexName) throws IOException;

    List<String> esAggIndex(String indexName,String aggName,String fileName) throws IOException;
    void deleteIndex(String indexName) throws IOException;

    boolean testExist(String indexName) throws IOException;
}
