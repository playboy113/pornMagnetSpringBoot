package com.play.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.play.config.RedisUtils;
import com.play.entity.magnet_model;
import com.play.fresh.db.MySqlControl;
import com.play.service.playMenuService;
import com.play.service.typesService;
import jcifs.smb.SmbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.*;

@Controller
@RequestMapping("/playMenu")
public class playMenuController {
    @Autowired
    private playMenuService playMenuService;
    @Autowired
    private typesService typesService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/queryForAllActressInMysql")
    @ResponseBody
    public Object queryForAllActressInMysql(){

        Map<String, List<String>> retMap = new HashMap<>();
        retMap.put("actress",playMenuService.selectForAllActressInMysql());





        return retMap;
    }
    //起手先把所有硬盘里map4文件存入sql空间
    @RequestMapping("/freshAllVideos.do")
    public Object freshAllVideos() throws SmbException, MalformedURLException {
        System.out.println("已經進入fresh层");
        //找出本地列表
        List<String> videoList = playMenuService.selectLocalFiles();
        List<String> numList = playMenuService.selectAllNums();
        Map<String, String> resultMap = new HashMap<String,String>();

        for (String v:videoList){
            for(String n:numList){
                if(v.toUpperCase().contains(n.toUpperCase()) || v.toUpperCase().contains(n.toUpperCase().replaceAll("-",""))){
                    System.out.println(v);
                    String s = v.replaceAll(":","/").replaceAll("\\\\","/");
                    resultMap.put(n,"/"+s);

                    //System.out.println(s.toUpperCase());

                }
            }
        }
        System.out.println("准备插入表");

        MySqlControl.executeInsert(resultMap);
        return null;


    }
    @RequestMapping("/queryForActressInLocal")
    @ResponseBody
    @Cacheable(value="queryForActressInLocal")
    public Object queryForActressInLocal() throws SmbException, MalformedURLException {



        LinkedHashMap<String, List<String>> actressMap = new LinkedHashMap<>();
        List<String> numsInLocates = playMenuService.selectNumsInLocates();
        List<String> actressByNums = playMenuService.selectActressByNums(numsInLocates);

        actressMap.put("actress",actressByNums);
        return actressMap;

    }
    @RequestMapping("/qeuryLocalFileNames")
    public void qeuryLocalFileNames() throws SmbException, MalformedURLException {
        System.out.println(playMenuService.selectLocalFiles());
    }
    @RequestMapping("/queryForVideosBySelectActress.do")
    @ResponseBody
    public Object queryForVideosBySelectActress(String selectActress) throws SmbException, MalformedURLException {
        //这个女优全部的番号
        List<String> numListInMysql = playMenuService.selectNumByActress(selectActress);
        //本地的所有番号
        List<String> numsInLocates = playMenuService.selectNumsInLocates();
        numsInLocates.retainAll(numListInMysql);
        System.out.println(numsInLocates);
        Map<String, String> resultMap = new HashMap<String,String>();
        for(String n:numsInLocates){
                    //resultMap.put(n,"/"+s);
            String s = playMenuService.selectLocateByNum(n);
            String locate = s.replaceAll(":","/").replaceAll("\\\\","/");
            resultMap.put(n,locate);
            System.out.println(locate);
        }
        return resultMap;
    }
    @RequestMapping("/showVideo")
    public void showVideo(HttpServletResponse response, @RequestParam("fileName")String fileName) {
        show(response,fileName,"video");
    }
    public void  show(HttpServletResponse response, String fileName,String type){
        try{
            FileInputStream fis = new FileInputStream(fileName); // 以byte流的方式打开文件
            int i=fis.available(); //得到文件大小
            byte data[]=new byte[i];
            fis.read(data);  //读数据
            response.setContentType(type+"/*"); //设置返回的文件类型
            OutputStream toClient=response.getOutputStream(); //得到向客户端输出二进制数据的对象
            toClient.write(data);  //输出数据
            toClient.flush();
            toClient.close();
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("文件不存在");
        }
    }

    @RequestMapping("/queryAllTypesInLocal.do")
    @ResponseBody
    public Object queryAllTypesInMysql(){
        Map<String, List<String>> retMap = new HashMap<>();
        List<String> typesList = typesService.selectAllTypes();
        System.out.println(typesList);
        retMap.put("typesList",typesList);
        return retMap;
    }
    @RequestMapping("/queryVideosByType.do")
    @ResponseBody
    public Object queryVideosByType(String type) throws SmbException, MalformedURLException {
         String[] types= JSON.parseArray(type).toArray(new String[0]);

        //该种类下的所有番号
        List<String> numList = typesService.selectVideosByType(types);
        //本地所有番号
        List<String> numsInLocates = playMenuService.selectNumsInLocates();
        //两个集合的交集
        numsInLocates.retainAll(numList);

        Map<String, String> resultMap = new HashMap<String,String>();
        for(String n:numsInLocates){
            String s = playMenuService.selectLocateByNum(n);
            String locate = s.replaceAll(":","/").replaceAll("\\\\","/");
            resultMap.put(n,locate);




        }

        return resultMap;


    }
    @RequestMapping("/localPornes.do")
    @ResponseBody
    public Object queryLocalPornes(String title, String actress, String subline, String HD, String num, String types, String date,String producer) throws SmbException, MalformedURLException {
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("actress",actress);
        map.put("subline",subline);
        map.put("HD",HD);
        map.put("num",num);
        map.put("types",types);

        map.put("date",date);
        map.put("producer",producer);
        //根据条件在mysql数据库中把所有的对象都找出来
        List<magnet_model> magnetModelList = playMenuService.queryMagnetByConditions(map);

        //本地磁盘中片名
        List<String> videoList = playMenuService.selectLocalFiles();
        //最后的结果
        Map retMap = new HashMap<String,magnet_model>();

        for (magnet_model m:magnetModelList){
            for (String v:videoList){
                if (v.toUpperCase().contains(m.getNum())){
                    String s = v.replaceAll(":","/").replaceAll("\\\\","/");
                    retMap.put(s,m);

                }
            }
        }
        return retMap;

    }

    @RequestMapping("/porndos.do")
    @ResponseBody
    public  Object queryByConditionForPage(String title, String actress, String subline, String HD, String num, String types, String date,String producer,Integer pageNo, Integer pageSize) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("actress",actress);
        map.put("subline",subline);
        map.put("HD",HD);
        map.put("num",num);
        map.put("types",types);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("date",date);
        map.put("producer",producer);

        List<magnet_model> magnet_models = playMenuService.queryMagnetByConditions(map);
        int totalRows = 50;
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("magnet_models",magnet_models);
        retMap.put("totalRows",totalRows);
        return retMap;




    }





}
