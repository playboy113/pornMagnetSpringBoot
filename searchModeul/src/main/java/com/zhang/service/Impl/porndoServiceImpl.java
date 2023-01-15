package com.zhang.service.Impl;

import com.zhang.entity.magnet_model;

import com.zhang.mapper.magnet_modelMapper;
import com.zhang.service.porndoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
@Slf4j
@Service("porndoService")
public class porndoServiceImpl implements porndoService {
    @Autowired
    private magnet_modelMapper magnet_modelMapper;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<magnet_model> selectAll() {
        List<magnet_model> magnetLists = magnet_modelMapper.selectAll();

        return magnetLists;
    }

    @Override
    public int queryCountOfMagnet(Map<String, Object> map) {
        return magnet_modelMapper.queryCountOfMagnet(map);
    }

    @Override
    public List<magnet_model> queryMagnetByConditions(Map<String, Object> map) {
        return magnet_modelMapper.queryMagnetByConditions(map);
    }

    @Override
    public String queryMagnetByNum(String num) {
        return magnet_modelMapper.queryMagnetByNum(num);
    }

    @Override
    public List<String> queryMagnetsByNums(String[] nums) {
        log.info("service层");
        return magnet_modelMapper.queryMagnetsByNums(nums);
    }

    @Override
    public List<String> queryAggIndex(String indexName, String aggName, String fileName) {

        String url = "http://localhost:8082/pornMagnet/aggRequest?indexName="+indexName+"&aggName="+aggName+"&fileName="+fileName;
        List aggList = restTemplate.getForObject(url, List.class);
        return aggList;



    }

    @Override
    public List<magnet_model> queryMagnetBySelectTypes(Map<String, Object> map) {
        return magnet_modelMapper.queryMagnetBySelectTypes(map);
    }

    @Override
    public int queryPagesBytypes(Map<String, Object> map) {
        return magnet_modelMapper.queryPagesByTypes(map);
    }


}
