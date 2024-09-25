package com.zhang.crawer.javmain;

import com.zhang.commons.setHeader;
import com.zhang.crawer.db.MySqlControl;
import com.zhang.crawer.entity.magnet_model;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.ComponentScan;
import com.ibm.icu.text.Transliterator;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@ComponentScan
public class crawer_books {


    public  ArrayList<magnet_model> javbooks(String url) throws IOException, URISyntaxException {


//        String url = "https://javbooks.com/serchinfo_censored/13/categorybt_1.htm";

        ArrayList<magnet_model> model_list = new ArrayList<>();


        //设置请求头
        crawer_books.Builder builder = new crawer_books.Builder();
        setHeader.setUp();
        builder.host = "https://javbooks.com/";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);


        Connection conn = Jsoup.connect(url);
        Connection conHeader = conn.headers(header);








        try{
            Document doc = conHeader.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();


            Elements elements = doc.getElementsByClass("Po_topic_title");
            for (Element element : elements) {
                String inner_url = element.select("a").attr("href");



                Document inner_doc = Jsoup.connect( inner_url).timeout(Integer.MAX_VALUE).get();


                magnet_model model = new magnet_model();
                //获取标题
                String str = inner_doc.getElementsByAttributeValue("id","title").text();

                model.setTitle(str);

                List<String> infobox = inner_doc.getElementsByClass("infobox").eachText();
                //获取番号

                if (infobox.get(0).indexOf(' ')== -1){
                    model.setNum(infobox.get(0).substring(infobox.get(0).indexOf('：')+1));
                    System.out.println(infobox.get(0).substring(infobox.get(0).indexOf('：') + 1));
                }else{
                    model.setNum(infobox.get(0).substring(infobox.get(0).indexOf('：')+1,infobox.get(0).indexOf(' ')));
                    System.out.println(infobox.get(0).substring(infobox.get(0).indexOf('：') + 1, infobox.get(0).indexOf(' ')));
                }

                //获取日期
                model.setDate(infobox.get(1).substring(infobox.get(1).indexOf('：')+1));

                //获取片商
                model.setProducer(infobox.get(4).substring(infobox.get(4).indexOf('：') + 1));

                //获取系列
                model.setSeries(infobox.get(6).substring(infobox.get(6).indexOf('：') + 1));

                //获取女优

                model.setActress(infobox.get(8).substring(infobox.get(8).indexOf('：') + 1));

                //获取类别
                Transliterator simplToTrad = Transliterator.getInstance("Simplified-Traditional");
                String traditionalChinese = simplToTrad.transliterate(infobox.get(7).substring(infobox.get(7).indexOf('：') + 1));
                model.setTypes(traditionalChinese);


                //获取封面图
                String coverImg = inner_doc.getElementsByAttributeValue("class", "info_cg").select("img").attr("src");
                downloadImages(model.getNum(),coverImg);


                //获取磁链
                Document javPage = Jsoup.connect("https://btsow.motorcycles/search/" + model.getNum()).timeout(Integer.MAX_VALUE).get();
                Thread.sleep(5000);

                //获取画质与字幕、磁力链接
                List<String> strings = javPage.getElementsByClass("row").select("a").eachAttr("href");
                List<String> magnetLists = javPage.getElementsByClass("row").eachText();
                List<String> sizes = javPage.getElementsByClass("col-sm-2 col-lg-1 hidden-xs text-right size").eachText();
                strings.removeIf(item -> !item.contains("hash"));
                magnetLists.removeIf(item -> !item.contains("Size:"));


                String magnetStr=null;
                int sublineHD = -1;
                int onlyHD = -1;

                for (int i =0;i< magnetLists.size();i++){

                    if (sizes.get(i).contains("GB")&&Double.parseDouble(sizes.get(i).substring(0,sizes.get(i).indexOf("GB")))>3 &&(magnetLists.get(i).contains("ch")||magnetLists.get(i).contains("CH")||magnetLists.get(i).contains("-C"))){
                        sublineHD = i;



                    } else if (sizes.get(i).contains("GB")&&Double.parseDouble(sizes.get(i).substring(0,sizes.get(i).indexOf("GB")))>3) {
                        onlyHD=i;

                    }


                }
                if (sublineHD !=-1){

                    magnetStr = "magnet:?xt=urn:btih:"+strings.get(sublineHD).substring(strings.get(sublineHD).indexOf("hash/")+5);
                    model.setMagenet(magnetStr);

                    model.setSubline("中文字幕");
                    model.setHD("高清");

                } else if (onlyHD !=-1) {
                    magnetStr = "magnet:?xt=urn:btih:"+strings.get(onlyHD).substring(strings.get(onlyHD).indexOf("hash/")+5);
                    model.setMagenet(magnetStr);

                    model.setSubline("无");
                    model.setHD("高清");


                }else {
                    magnetStr = "magnet:?xt=urn:btih:"+strings.get(0).substring(strings.get(0).indexOf("hash/")+5);
                    model.setMagenet(magnetStr);

                    model.setSubline("无");
                    model.setHD("无");
                }


                model_list.add(model);
                MySqlControl.executeInsert(model);

            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return model_list;
    }
    public void downloadImages(String fileName,String ImageUrl){
        URL urlObj = null;
        URLConnection conn=null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        //String savePath = "D:\\github\\pornMagnetSpringBoot\\searchModeul\\src\\main\\resources\\images";
        String savePath = "E:\\images\\";
        OutputStream outputStream=null;
        BufferedOutputStream bos=null;
        try{
            urlObj = new URL(ImageUrl);
            conn = urlObj.openConnection();
            conn.setConnectTimeout(3*100000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("User-Agent", "Mozilla/4.76");
            inputStream = conn.getInputStream();
            bis = new BufferedInputStream(inputStream);

            File saveDir = new File(savePath);
            if (!saveDir.exists()){
                saveDir.mkdirs();
            }
            String filePath = savePath+File.separator+fileName.trim()+".jpg";
            File file = new File(filePath);
            outputStream = new FileOutputStream(file);
            bos = new BufferedOutputStream(outputStream);
            byte[] buffer = new byte[1024];
            int len=0;
            while ((len = bis.read(buffer))!= -1){
                bos.write(buffer,0,len);
            }
            //System.out.println("info:"+ImageUrl+"download success,fileName="+fileName);




        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (bis!=null){
                    bis.close();

                }
                if(bos != null){
                    bos.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }







     class Builder{
        //设置userAgent库;读者根据需求添加更多userAgent
        String[] userAgentStrs = {"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)"};
        List<String> userAgentList = Arrays.asList(userAgentStrs);
        int userAgentSize = userAgentList.size();
        //设置referer库;读者根据需求添加更多referer
        String[] refererStrs = {"https://www.baidu.com/",
                "https://www.sogou.com/",
                "http://www.bing.com",
                "https://www.so.com/"};
        List<String> refererList = Arrays.asList(refererStrs);
        int refererSize = refererList.size();
        //设置Accept、Accept-Language以及Accept-Encoding
        String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
        String acceptLanguage = "zh-cn,zh;q=0.5";
        String acceptEncoding = "gzip, deflate";
        String host;
    }
}
