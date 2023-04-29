package com.play.service;

import jcifs.smb.SmbException;

import java.net.MalformedURLException;
import java.util.List;

public interface playMenuService {

    List<String> selectForAllActressInMysql();
    List<String> selectLocalFiles() throws SmbException, MalformedURLException;

    List<String> selectNumByActress(String actress);

    List<String> selectActressByNum(String num);
    List<String> selectActressByNums(List<String> nums);

    List<String> selectNumInDB();
}
