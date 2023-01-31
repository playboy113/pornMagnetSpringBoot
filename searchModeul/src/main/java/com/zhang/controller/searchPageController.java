package com.zhang.controller;

import com.zhang.commons.setHeader;
import com.zhang.crawer.entity.magnet_model;

import com.zhang.crawer.javmain.crawer_javdb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class searchPageController {

    @RequestMapping("/crawerMagnet.do")
    @ResponseBody
    public Map<String,Object> crawerMagnet(String magnetUrl,Integer pages) throws IOException, URISyntaxException {
        setHeader.setUp();
        if(magnetUrl.contains("page=")){

            int result =0;
            result = magnetUrl.indexOf("page=");
            String begin = magnetUrl.substring(0,result+5);
            String end=magnetUrl.substring(result+6,magnetUrl.length());
            System.out.println("一共准备下载"+pages+"页");
            for (int i =1;i<pages;i++){
                String url = magnetUrl;
                crawer_javdb crawer_javdb = new crawer_javdb();
                System.out.println("正在下載第"+i+"/"+pages+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javdb(begin+i+end);

                //MySqlControl.executeInsert(javdb);
            }





        }else{
            pages = 2;
            for (int i =1;i<pages;i++){
                String url = magnetUrl;
                crawer_javdb crawer_javdb = new crawer_javdb();
                ArrayList<magnet_model> javdb = crawer_javdb.javdb(url);
                //MySqlControl.executeInsert(javdb);
            }
        }
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("result","完成");
        return retMap;
    }
//    @RequestMapping("/index.do")
//    public String porndoList(HttpServletRequest request){
//        return "porndos";
//    }

}
