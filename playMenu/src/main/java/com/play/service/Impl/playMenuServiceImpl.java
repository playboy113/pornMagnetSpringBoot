package com.play.service.Impl;

import com.play.entity.magnet_model;
import com.play.mapper.magnet_actressMapper;
import com.play.mapper.magnet_modelMapper;
import com.play.service.playMenuService;

import com.play.tools.getLocalFileNamesUserFile;
import jcifs.smb.SmbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@Service("playMenu")
public class playMenuServiceImpl implements playMenuService {
    @Autowired
    private magnet_actressMapper magnet_actressMapper;
    @Autowired
    private magnet_modelMapper magnet_modelMapper;

    @Override
    public List<String> selectForAllActressInMysql() {


        return magnet_actressMapper.selectForAllActressInMysql();
    }

    @Override
    @Cacheable(value="selectLocalFiles")
    public List<String> selectLocalFiles() throws SmbException, MalformedURLException {
        List<String> localFileNames = new getLocalFileNamesUserFile().getFileNames();
        return localFileNames;
    }

    @Override
    public List<String> selectNumByActress(String actress) {

        List<String> numList = magnet_actressMapper.selectNumByActress(actress);

        return numList;
    }

    @Override
    public List<String> selectActressByNum(String num) {
        return magnet_actressMapper.selectActressByNum(num);
    }

    @Override
    @Cacheable(value = "selectActressByNums")
    public List<String> selectActressByNums(List<String> nums) {
        return magnet_actressMapper.selectActressByNums(nums);
    }

    @Override
    @Cacheable(value="selectNumInDB")
    public List<String> selectNumInDB() {
        return magnet_actressMapper.selectNumInDB();
    }

    @Override
    public List<magnet_model> queryMagnetByConditions(Map<String, Object> map) {
        return magnet_modelMapper.queryMagnetByConditions(map);
    }

    @Override
    public List<String> selectAllNums() {
        return magnet_actressMapper.selectAllNums();
    }

    @Override
    public List<String> selectNumsInLocates() {
        return magnet_actressMapper.selectAllNumsInLocate();
    }

    @Override
    public String selectLocateByNum(String num) {
        return magnet_actressMapper.selectLocateByNum(num);
    }
}
