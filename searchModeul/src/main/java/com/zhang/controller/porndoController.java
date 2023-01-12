package com.zhang.controller;

import com.zhang.entity.User;
import com.zhang.entity.magnet_model;
import com.zhang.service.porndoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/pornMagnet")
public class porndoController {
    @Autowired
    private porndoService porndoService;
    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
//        List<magnet_model> magnet_models = porndoService.selectAll();
//        request.setAttribute("magnet_models",magnet_models);
        return "porndos";
    }

    @RequestMapping("/selectAll")
    public List<magnet_model> selectAll(){
        List<magnet_model> magnetList = porndoService.selectAll();
        return magnetList;

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

        List<magnet_model> magnet_models = porndoService.queryMagnetByConditions(map);
        int totalRows = porndoService.queryCountOfMagnet(map);
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("magnet_models",magnet_models);
        retMap.put("totalRows",totalRows);
        return retMap;



//        List<magnet_model> magnet_models = porndoService.selectAll();
//        Map<String,Object> retMap = new HashMap<>();
//        retMap.put("magnet_models",magnet_models);
        //log.info("retMap:{}",retMap);
//        return retMap;

    }
    @RequestMapping("/toServer.do")
    @ResponseBody
    public Map<String,String> print(User user){
        Map<String,String> map = new HashMap<>();
        if(user!=null){
            map.put("result","成功");

        }else{
            map.put("result","失败");
        }
        return map;

    }
    @RequestMapping("/copyMagnet.do")
    @ResponseBody
    public    Map<String,Object> copyMagnet(String num,HttpServletRequest request){
        Map<String,Object> retMap = new HashMap<>();
        String retMagnet = porndoService.queryMagnetByNum(num);
        System.out.println(num);

        retMap.put("magnet",retMagnet);
        return retMap;
    }
    @RequestMapping("/queryMagnetsByNums.do")
    @ResponseBody
    public Map<String,Object> selectMagnetsByNums(String nums){
        ArrayList<String> nums2 = new ArrayList<>();
        //将字符串转换为数组用于sql语句
        char ch[] =nums.toCharArray();
        int slow=0,fast=0;
        while (fast <ch.length){
            if (ch[fast]=='&'){
                String str=nums.substring(slow,fast);
                fast = fast+1;
                slow=fast;
                nums2.add(str);
            }
            fast++;
        }
        String[] num3 = new String[nums2.size()];
        for (int i=0;i<nums2.size();i++){
            num3[i] = nums2.get(i);
        }
        Map<String,Object> retMap = new HashMap<>();


        List<String> magnetList = porndoService.queryMagnetsByNums(num3);
        String retStr = "";
        for (int i=0;i<magnetList.size();i++){
            retStr += magnetList.get(i)+"\n";
        }

        retMap.put("magnetList",magnetList);
        retMap.put("retStr",retStr);
        return retMap;
    }
    @RequestMapping("/searchPage.do")
    public String searchPage(){
        return "search";
    }

    @RequestMapping("/aggRequest.do")
    public Map<String,Object> aggIndex(@PathParam("indexName") String indexName, @PathParam("aggName") String aggName, @PathParam("fileName") String fileName){
        Map<String,Object> retMap = new HashMap<>();
        List<String> aggList = porndoService.queryAggIndex(indexName,aggName,fileName);

        retMap.put("aggList",aggList);
        return retMap;

    }

}
