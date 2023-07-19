package com.flowers.service;

import com.flowers.entity.flower;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface flowService {
    List<flower> selectAll();

    void insertNew(Map<String,Object> map);
}
