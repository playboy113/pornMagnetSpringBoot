package com.zhang.service.Impl;

import com.zhang.entity.PornTypes;
import com.zhang.mapper.PornTypesMapper;
import com.zhang.service.typesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("typeService")
public class typesServceImpl implements typesService {
    @Autowired
    private PornTypesMapper pornTypesMapper;
    @Override
    public List<PornTypes> selectAll() {
        return pornTypesMapper.selectAll();

    }
}
