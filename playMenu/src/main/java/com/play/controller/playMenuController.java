package com.play.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.play.config.RedisUtils;
import com.play.entity.magnet_model;
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
    @RequestMapping("/queryForActressInLocal")
    @ResponseBody
    @Cacheable(value="queryForActressInLocal")
    public Object queryForActressInLocal() throws SmbException, MalformedURLException {
        LinkedHashMap<String, List<String>> actressMap = new LinkedHashMap<>();
        List<String> videoList = playMenuService.selectLocalFiles();
        List<String> numList = playMenuService.selectNumInDB();
        List<String> slectNumList=new ArrayList<>();
        if(Boolean.FALSE.equals(redisUtils.hasKey("slectNumList"))){
            System.out.println("查询mysql数据库");
            int flag=1;
            for(String n:numList){
                for (String v:videoList){
                    if(v.toUpperCase().contains(n)){
                        slectNumList.add(n);
                        System.out.println("正在开通"+(flag+=1));
                    }
                }
            }
        }else{
            slectNumList = Collections.singletonList(redisUtils.get("slectNumList"));
        }
        List<String> actressList = playMenuService.selectActressByNums(slectNumList);
        actressMap.put("actress",actressList);
        return actressMap;

    }
    @RequestMapping("/qeuryLocalFileNames")
    public void qeuryLocalFileNames() throws SmbException, MalformedURLException {
        System.out.println(playMenuService.selectLocalFiles());
    }
    @RequestMapping("/queryForVideosBySelectActress.do")
    @ResponseBody
    public Object queryForVideosBySelectActress(String selectActress) throws SmbException, MalformedURLException {

        List<String> numList = playMenuService.selectNumByActress(selectActress);
        List<String> videoList = playMenuService.selectLocalFiles();



        Map<String, String> resultMap = new HashMap<String,String>();
        for(String n:numList){
            for (String v:videoList){
                if(v.toUpperCase().contains(n)){
                    String s = v.replaceAll(":","/").replaceAll("\\\\","/");
                    resultMap.put(n,"/"+s);

                    System.out.println(s.toUpperCase());

                }
            }
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

    @RequestMapping("/queryAllTypesInMysql.do")
    @ResponseBody
    public Object queryAllTypesInMysql(){
        Map<String, List<String>> retMap = new HashMap<>();
        List<String> typesList = typesService.selectAllTypes();
        retMap.put("typesList",typesList);
        return retMap;
    }
    @RequestMapping("/queryVideosByType.do")
    @ResponseBody
    public Object queryVideosByType(String type) throws SmbException, MalformedURLException {
         String[] types= JSON.parseArray(type).toArray(new String[0]);


        List<String> numList = typesService.selectVideosByType(types);

        List<String> videoList = playMenuService.selectLocalFiles();



        Map<String, String> resultMap = new HashMap<String,String>();
        for(String n:numList){
            for (String v:videoList){
                if(v.toUpperCase().contains(n)){
                    String s = v.replaceAll(":","/").replaceAll("\\\\","/");
                    resultMap.put(n,"/"+s);

                    System.out.println(s.toUpperCase());

                }
            }
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





}
