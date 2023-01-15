package com.zhang.controller;

import com.zhang.service.EsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

import static com.zhang.commons.PornIndexConstants.MAPPING_TEMPLATE;
import static com.zhang.commons.PornIndexConstants.porn_types;

@Slf4j
@RestController
@RequestMapping("/pornMagnet")
public class esPorndoController {
    @Autowired
    private EsSearchService esSearchService;
    @RequestMapping("/test")
    public void test(){
        System.out.println("打印成功");
    }

    @RequestMapping("/bulkRequest/{indexName}")
    public String bulkRequest(@PathVariable("indexName") String indexName) throws IOException {
        esSearchService.bulkRequest(indexName);
        return "suceess";
    }
    @RequestMapping("/aggRequest")
    public List<String> aggRequest(@PathParam("indexName") String indexName,@PathParam("aggName") String aggName,@PathParam("fileName") String fileName) throws IOException {

        return esSearchService.esAggIndex(indexName,aggName,fileName);

    }




    @RequestMapping("/createIndex.do/{indexName}")
    public void createIndex(@PathVariable("indexName") String indexName) throws IOException {


        esSearchService.createIndex(indexName);
        System.out.println("搞定");

    }





}
