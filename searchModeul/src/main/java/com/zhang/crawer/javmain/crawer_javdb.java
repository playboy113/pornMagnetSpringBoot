package com.zhang.crawer.javmain;

import com.zhang.crawer.db.MySqlControl;
import com.zhang.crawer.entity.magnet_model;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@ComponentScan
public class crawer_javdb {

    public ArrayList<magnet_model> javdb(String url) throws IOException, URISyntaxException {


        ArrayList<magnet_model> model_list = new ArrayList<>();


        //设置请求头
        Builder builder = new Builder();
        builder.host = "https://javdb.com/";
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
            Elements elements = doc.getElementsByClass("item");
            for (Element element : elements) {
                String inner_url = element.select("a").attr("href");

                Document inner_doc = Jsoup.connect("https://javdb.com/" + inner_url).timeout(1000*10).get();
                magnet_model model = new magnet_model();




                //获取番号
                model.setNum(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text().trim());

                //获取封面图片并下载
                String coverImg = inner_doc.getElementsByAttributeValue("class", "video-cover").attr("src");
                downloadImages(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text(),coverImg);

                //获取标题
                String str = inner_doc.getElementsByTag("h2").text();
                if (str.length()==0){
                    model.setTitle(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text());
                }else{
                    model.setTitle(str.substring(str.indexOf(" "), str.length()-1));
                }

                String[] typesArr=null;
                String[] actressArr=null;
                //获取演员与類別以及片商
                Elements dess = inner_doc.getElementsByClass("panel-block");
                for (Element des : dess) {
                    if (des.select("strong").text().contains("演員")) {
                        model.setActress(des.select("a").text());

                        String[] newActressArr = des.select("a").text().split(" ");
                        actressArr = new String[newActressArr.length];
                        for (int i=0;i< newActressArr.length;i++){
                            actressArr[i] = newActressArr[i];
                        }



                    } else if (des.select("strong").text().contains("類別")) {
                        model.setTypes(des.select("a").text());
                        String[] newTypesArr = des.select("a").text().split(" ");
                        typesArr = new String[newTypesArr.length];
                        for (int i=0;i< newTypesArr.length;i++){
                                typesArr[i] = newTypesArr[i];
                        }


                    }else if(des.select("strong").text().contains("日期")){
                        model.setDate(des.select("span").text());
                    }else if(des.select("strong").text().contains("片商")){
                        model.setProducer(des.select("span").text());
                    }
                }
                //插入类别
                MySqlControl.insertTypes(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text().trim(),typesArr);
                MySqlControl.insertActress(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text().trim(),actressArr);

                //获取画质与字幕、磁力链接
                Elements inner_elements = inner_doc.getElementsByAttributeValue("class", "magnet-name column is-four-fifths");
                for (Element inner_ele : inner_elements) {
                    String inner_str = inner_ele.select("div").text();
                    if (inner_str.contains("高清") && inner_str.contains("字幕")) {
                        String magenet = inner_ele.select("a").attr("href");
                        magenet = magenet.replace(".torrent", "");
                        model.setMagenet(magenet);
                        model.setSubline("中文字幕");
                        model.setHD("高清");
                        break;
                    } else if (inner_str.contains("高清") && !inner_str.contains("字幕")) {
                        String magenet = inner_ele.select("a").first().attr("href");
                        magenet = magenet.replace(".torrent", "");
                        model.setMagenet(magenet);
                        model.setSubline("无");
                        model.setHD("高清");
                        break;
                    } else if (!inner_str.contains("高清") && inner_str.contains("字幕")) {
                        String magenet = inner_ele.select("a").first().attr("href");
                        magenet = magenet.replace(".torrent", "");
                        model.setMagenet(magenet);
                        model.setSubline("中文字幕");
                        model.setHD("无");
                        break;
                    } else {
                        String magenet = inner_ele.select("a").first().attr("href");
                        magenet = magenet.replace(".torrent", "");
                        model.setMagenet(magenet);
                        model.setSubline("无");
                        model.setHD("无");
                        break;
                    }

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
        String savePath = "D:\\images\\";
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
