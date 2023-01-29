package com.zhang.controller;

import com.zhang.commons.setHeader;
import com.zhang.crawer.entity.magnet_model;

import com.zhang.crawer.javmain.crawer_javdb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.websocket.server.PathParam;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class searchPageController {
    @RequestMapping("/test.do/{url}")
    public String test(@PathVariable("url") String url){
        System.out.println(url);
        return url;

    }

    @RequestMapping("/crawerMagnet.do")
    public Map<String,Object> crawerMagnet(@PathParam("magnetUrl") String magnetUrl,@PathParam("pages") Integer pages) throws IOException, URISyntaxException, ParserConfigurationException, SAXException {
        setHeader.setUp();
        magnetUrl="https://javdb.com/actors/Nw1wN?page=2&t=d";
        if(magnetUrl.contains("page=")){

            int result =0;
            result = magnetUrl.indexOf("page=");
            String begin = magnetUrl.substring(0,result+5);
            String end=magnetUrl.substring(result+6);
            System.out.println(result+6);
            System.out.println(begin);
            System.out.println(end);
            for (int i =1;i<pages;i++){
                String url = magnetUrl;
                crawer_javdb crawer_javdb = new crawer_javdb();
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
