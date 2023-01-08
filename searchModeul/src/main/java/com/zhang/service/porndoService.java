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
}
