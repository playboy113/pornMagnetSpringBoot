package com.flowers.service.Impl;

import com.flowers.entity.flower;
import com.flowers.mapper.FlowlistsMapper;
import com.flowers.service.flowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("flowService")
public class flowServiceImpl implements flowService {
    @Autowired
    private FlowlistsMapper flowlistsMapper;


    @Override
    public List<flower> selectAll() {
        List<flower> flowers = flowlistsMapper.selectAll();
        return flowers;
    }

    @Override
    public void insertNew(Map<String, Object> map) {
        flowlistsMapper.insertNew(map);
    }
}
