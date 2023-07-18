package com.play.service.Impl;

import com.play.mapper.magnet_typesMapper;
import com.play.service.typesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("typesService")
public class typesServiceImpl implements typesService {
    @Autowired
    private magnet_typesMapper magnet_typesMapper;

    public List<String> selectAllTypes(){
        return magnet_typesMapper.selectAllTypes();
    }

    @Override
    public List<String> selectVideosByType(String[] types) {

        return magnet_typesMapper.selectVideosByType(types);
    }
}
