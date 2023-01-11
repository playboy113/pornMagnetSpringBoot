package com.zhang;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Slf4j
@SpringBootApplication
public class elasticSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(elasticSearchApplication.class);
    }
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.47.129:9200")
        ));
    }
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }


}
