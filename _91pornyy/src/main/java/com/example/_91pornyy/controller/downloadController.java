package com.example._91pornyy.controller;

import com.example._91pornyy.crawer._91pornyCrawer;
import com.example._91pornyy.crawer.setHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class downloadController {

    @RequestMapping("/downloadMagnet.do")
    @ResponseBody
    public Map<String,Object> crawerMagnet(String magnetUrl, Integer start_pages, Integer finish_pages) throws IOException, URISyntaxException {
        System.out.println("进来了");
        setHeader.setUp();
        int currentPage = start_pages;

        String currentUrl = magnetUrl.substring(0,magnetUrl.length()-1);
        System.out.println(currentUrl);
        while (currentPage <= finish_pages){
            _91pornyCrawer pornyCrawer = new _91pornyCrawer();
            pornyCrawer._91pornyCrawer(currentUrl+currentPage);
            currentPage = currentPage+1;

        }






//    @RequestMapping("/index.do")
//    public String porndoList(HttpServletRequest request){
//        return "porndos";

        Map<String,Object> retMap = new HashMap<>();
        retMap.put("result","完成");
        return retMap;
//    }
    }

}
