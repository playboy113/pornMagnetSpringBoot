package com.play.service.Impl;

import com.play.mapper.magnet_actressMapper;
import com.play.service.playMenuService;
import com.play.tools.getLocalFileNames;
import com.play.tools.getLocalFileNamesUserFile;
import jcifs.smb.SmbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;

@Service("playMenu")
public class playMenuServiceImpl implements playMenuService {
    @Autowired
    private magnet_actressMapper magnet_actressMapper;

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
}
