package com.zhang.controller;

import com.alibaba.fastjson.JSON;
import com.zhang.crawer.db.MySqlControl;
import com.zhang.crawer.javmain.playHtml;
import com.zhang.entity.User;
import com.zhang.entity.magnet_model;
import com.zhang.service.porndoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/pornMagnet")
public class porndoController {
    @Autowired
    private porndoService porndoService;
    @RequestMapping("/index")
    @ResponseBody
    public Object index(HttpServletRequest request){
//        List<magnet_model> magnet_models = porndoService.selectAll();
//        request.setAttribute("magnet_models",magnet_models);
        Map<String,Object> retMap = new HashMap<>();
        List<magnet_model> magnet_models = porndoService.selectAll();
        List<magnet_model> magnet_models1 = magnet_models.subList(1, 50);
        //根据查找到的列表匹配播放地址
        playHtml playHtml = new playHtml();
        for(magnet_model model1:magnet_models1){
            model1.setLocate(playHtml.playingHtml(model1.getNum()));
        }
        retMap.put("magnet_models",magnet_models1);
        int pageNums = magnet_models.size()/50;

        retMap.put("pageNums",pageNums);
        return new ModelAndView("result",retMap);

    }

    @RequestMapping("/selectAll")
    public List<magnet_model> selectAll(){
        List<magnet_model> magnetList = porndoService.selectAll();
        return magnetList;

    }

    @RequestMapping (value = "/porndos.do")
    @ResponseBody
    public  Object queryByConditionForPage(String title, String actress, String subline, String HD, String num, String types, String date,String producer,String series,Integer pageNo, Integer pageSize,Model model) throws IOException {
        System.out.println("進來了");
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("actress",actress);
        map.put("subline",subline);
        map.put("HD",HD);
        map.put("num",num);
        map.put("types",types);
        map.put("beginNo",1);
        map.put("pageSize",pageSize);
        map.put("date",date);
        map.put("producer",producer);
        map.put("series",series);



        List<magnet_model> magnet_models = porndoService.queryMagnetByConditions(map);
        int totalRows = porndoService.queryCountOfMagnet(map);
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("magnet_models",magnet_models);
        retMap.put("totalRows",totalRows);

        model.addAttribute("magnet_models",magnet_models);
//        return new ModelAndView("result",retMap);
        return "result";




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
        System.out.println("单个copy的controller层");
        Map<String,Object> retMap = new HashMap<>();
        String retMagnet = porndoService.queryMagnetByNum(num);
        System.out.println("magnet为："+retMagnet);
        porndoService.insertSelectedRow(num);
        //System.out.println(num);

        retMap.put("magnet",retMagnet);
        return retMap;
    }
    //播放
    @RequestMapping("/playVideo.do")
    @ResponseBody
    public Map<String,Object> playVideo(String num){

        playHtml playHtml = new playHtml();
        String videoUrl = playHtml.playingHtml(num);


        Map<String,Object> retMap = new HashMap<>();
        retMap.put("url1",videoUrl);
        return retMap;
    }
    //收集播放链接
    @RequestMapping("/colVideoUrl.do")
    @ResponseBody
    public Map<String,Object> VideoUrl() throws SQLException {
        String num = null;
        List<magnet_model> magnet_models = porndoService.selectAll();
        playHtml playHtml = new playHtml();
        MySqlControl mySqlControl = new MySqlControl();

        //哪些num已经有html了
        List<String> havaUrlList = porndoService.haveUrlNum();


        for(magnet_model model:magnet_models){
           num =  model.getNum();
           if (!havaUrlList.contains(num)){
                String videoUrl = playHtml.playingHtml(num);
                mySqlControl.insertNumUrls(num,videoUrl);
            }

        }


        //String videoUrl = playHtml.playingHtml(num);


        Map<String,Object> retMap = new HashMap<>();
        retMap.put("url1","123");
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

            porndoService.insertSelectedRow(nums2.get(i));
        }
        Map<String,Object> retMap = new HashMap<>();


        List<String> magnetList = porndoService.queryMagnetsByNums(num3);
        StringBuilder retStr = new StringBuilder();
        for (int i=0;i<magnetList.size();i++){
            retStr.append(magnetList.get(i));
            retStr.append("\r");
            retStr.append("\n");


        }
        String lineSeperator = System.lineSeparator();


        retMap.put("magnetList",magnetList);
        retMap.put("retStr", retStr);
        return retMap;
    }
    @RequestMapping("/searchPage.do")
    public String searchPage(){
        return "search";
    }

//    @RequestMapping("/aggRequest.do")
//    public Map<String,Object> aggIndex(@PathParam("indexName") String indexName, @PathParam("aggName") String aggName, @PathParam("fileName") String fileName){
//        Map<String,Object> retMap = new HashMap<>();
//        List<String> aggList = porndoService.queryAggIndex(indexName,aggName,fileName);
//
//        retMap.put("aggList",aggList);
//        return retMap;
//
//    }

    //根据选择的类别筛选
    @RequestMapping("/search")
    @ResponseBody
    public Object queryMagnetBySelectTypes(@RequestParam(value = "title",required = false) String title, @RequestParam(value = "actress",required = false) String actress,
                                           @RequestParam(value = "subline" ,required = false)String subline, @RequestParam(value = "HD",required = false)String HD,
                                           @RequestParam(value = "num",required = false)String num,@RequestParam(value = "types",required = false) String types,@RequestParam(value = "date",required = false) String date,
                                           @RequestParam(value = "producer",required = false)String producer,@RequestParam(value = "series",required = false)String series,@RequestParam(value = "pageNo",required = false)Integer pageNo,
                                           @RequestParam(value = "pageSize",required = false)Integer pageSize){
        //将types从string转换为数组
        //System.out.println(types);
        System.out.println("已经传进来了，types是:"+types);
        if (pageNo == null){
            pageNo =1;
        }
        if (pageSize == null){
            pageSize =50;
        }




        //System.out.println(Arrays.toString(typesArr));


        Map<String,Object> map = new HashMap<>();


        map.put("title",title);
        map.put("actress",actress);
        map.put("subline",subline);
        map.put("HD",HD);
        map.put("num",num);
        map.put("types",types);
        map.put("beginNo",(pageNo-1)*pageSize+1);
        map.put("pageSize",pageSize);
        map.put("date",date);
        map.put("producer",producer);
        map.put("series",series);



        List<magnet_model> magnet_models = porndoService.queryMagnetByConditions(map);
        //根据查找到的列表匹配播放地址
        playHtml playHtml = new playHtml();
        for(magnet_model model1:magnet_models){
            model1.setLocate(playHtml.playingHtml(model1.getNum()));

        }


//        int totalRows = porndoService.queryCountOfMagnet(map);
        Map<String,Object> retMap = new HashMap<>();

        retMap.put("magnet_models",magnet_models);
        retMap.put("totalRows",map.get("types"));
        map.replace("beginNo",pageNo);

        retMap.put("map",map);


        return new ModelAndView("result",retMap);

    }






}
