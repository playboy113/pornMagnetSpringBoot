package com.zhang.service;

import com.zhang.entity.magnet_model;

import java.util.List;
import java.util.Map;

public interface porndoService {
    List<magnet_model> selectAll();
    int queryCountOfMagnet(Map<String,Object> map);
    List<magnet_model> queryMagnetByConditions(Map<String,Object> map);

    String queryMagnetByNum(String num);

    List<String> queryMagnetsByNums(String[] nums);

    List<String> queryAggIndex(String indexName,String aggName,String fileName);
    List<magnet_model> queryMagnetBySelectTypes(Map<String,Object> map);

    int queryPagesBytypes(Map<String, Object> map);

    List<String> selectAllTypes();

    void insertSelectedRow(String num);

    List<magnet_model> selectBytype(String type);

    List<String> haveUrlNum();
}
