package com.play.service;

import com.play.entity.magnet_model;
import jcifs.smb.SmbException;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public interface playMenuService {

    List<String> selectForAllActressInMysql();
    List<String> selectLocalFiles() throws SmbException, MalformedURLException;

    List<String> selectNumByActress(String actress);

    List<String> selectActressByNum(String num);
    List<String> selectActressByNums(List<String> nums);

    List<String> selectNumInDB();
    List<magnet_model> queryMagnetByConditions(Map<String,Object> map);
}
