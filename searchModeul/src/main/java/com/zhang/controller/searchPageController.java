package com.zhang.controller;

import com.zhang.commons.setHeader;
import com.zhang.crawer.entity.magnet_model;

import com.zhang.crawer.javmain.crawer_books;
import com.zhang.crawer.javmain.crawer_javbooks_db;
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
    public Map<String,Object> crawerMagnet(String magnetUrl,Integer pages,Integer stop) throws IOException, URISyntaxException {
        setHeader.setUp();
        int result =0;
        if(magnetUrl.contains("page=")){


            result = magnetUrl.indexOf("page=");
            String begin = magnetUrl.substring(0,result+5);
            String end=magnetUrl.substring(result+6,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");
            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javdb crawer_javdb = new crawer_javdb();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
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
@RequestMapping("/crawerBooks.do")
@ResponseBody
public Map<String,Object> crawerBooks(String magnetUrl,Integer pages,Integer stop) throws IOException, URISyntaxException {
    setHeader.setUp();



    int result =0;
        if (magnetUrl.contains("topicsbt_")) {
            result = magnetUrl.indexOf("topicsbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }
        
        } else if (magnetUrl.contains("categorybt_")) {
            result = magnetUrl.indexOf("categorybt_");
            String begin = magnetUrl.substring(0,result+11);
            String end=magnetUrl.substring(result+12,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }
            
        }else if(magnetUrl.contains("performerbt_")){

            result = magnetUrl.indexOf("performerbt_");
            String begin = magnetUrl.substring(0,result+12);
            String end=magnetUrl.substring(result+13,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }
        } else if (magnetUrl.contains("issuerbt_")) {
            result = magnetUrl.indexOf("issuerbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }
            
        }else if (magnetUrl.contains("seriesbt_")) {
            result = magnetUrl.indexOf("seriesbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");


            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }

        }else if (magnetUrl.contains("makersbt_")) {
            result = magnetUrl.indexOf("makersbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }

        } else if (magnetUrl.contains("serialall_")) {
            result = magnetUrl.indexOf("serialall_");
            String begin = magnetUrl.substring(0,result+10);
            String end=magnetUrl.substring(result+11,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }
            
        }else if (magnetUrl.contains("serialbt_")) {
            result = magnetUrl.indexOf("serialbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_books crawer_javdb = new crawer_books();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks(begin+i+end);
                //MySqlControl.executeInsert(javdb);
            }

        }


    Map<String,Object> retMap = new HashMap<>();
    retMap.put("result","完成");
    return retMap;
}
    @RequestMapping("/crawerBooks_db.do")
    @ResponseBody
    public Map<String,Object> crawerBooks_db(String magnetUrl,Integer pages,Integer stop) throws IOException, URISyntaxException {
        setHeader.setUp();



        int result =0;
        if (magnetUrl.contains("topicsbt_")) {
            result = magnetUrl.indexOf("topicsbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+begin+i+".htm");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }

        } else if (magnetUrl.contains("categorybt_")) {
            result = magnetUrl.indexOf("categorybt_");
            String begin = magnetUrl.substring(0,result+11);
            String end=magnetUrl.substring(result+12,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }

        }else if(magnetUrl.contains("performerbt_")){

            result = magnetUrl.indexOf("performerbt_");
            String begin = magnetUrl.substring(0,result+12);
            String end=magnetUrl.substring(result+13,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }
        } else if (magnetUrl.contains("issuerbt_")) {
            result = magnetUrl.indexOf("issuerbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }

        }else if (magnetUrl.contains("seriesbt_")) {
            result = magnetUrl.indexOf("seriesbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");


            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }

        }else if (magnetUrl.contains("makersbt_")) {
            result = magnetUrl.indexOf("makersbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }

        } else if (magnetUrl.contains("serialall_")) {
            result = magnetUrl.indexOf("serialall_");
            String begin = magnetUrl.substring(0,result+10);
            String end=magnetUrl.substring(result+11,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }

        }else if (magnetUrl.contains("serialbt_")) {
            result = magnetUrl.indexOf("serialbt_");
            String begin = magnetUrl.substring(0,result+9);
            String end=magnetUrl.substring(result+10,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }


        }else if (magnetUrl.contains("categoryall_")) {
            result = magnetUrl.indexOf("categoryall_");
            String begin = magnetUrl.substring(0,result+12);
            String end=magnetUrl.substring(result+13,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }


        }else if (magnetUrl.contains("performerall_")) {
            result = magnetUrl.indexOf("performerall_");
            String begin = magnetUrl.substring(0,result+13);
            String end=magnetUrl.substring(result+14,magnetUrl.length());
            System.out.println("一共准备下载"+(stop-pages)+"页");

            for (int i =pages;i<stop;i++){
                String url = magnetUrl;
                crawer_javbooks_db crawer_javdb = new crawer_javbooks_db();
                System.out.println("正在下載第"+i+"/"+stop+"頁");
                ArrayList<magnet_model> javdb = crawer_javdb.javbooks_db(begin+i+".htm");
                //MySqlControl.executeInsert(javdb);
            }

        }


        Map<String,Object> retMap = new HashMap<>();
        retMap.put("result","完成");
        return retMap;
    }

}
