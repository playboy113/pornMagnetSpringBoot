package com.zhang.crawer.javmain;

import com.ibm.icu.text.Transliterator;
import com.zhang.commons.setHeader;
import com.zhang.crawer.db.MySqlControl;
import com.zhang.crawer.entity.magnet_model;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class crawer_javbooks_db {
    public ArrayList<magnet_model> javbooks_db(String url) throws IOException, URISyntaxException {

        ArrayList<magnet_model> model_list = new ArrayList<>();


        //设置请求头
        crawer_javbooks_db.Builder builder = new crawer_javbooks_db.Builder();
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
        header.put("Cookie", builder.Cookie);

        Connection conn = Jsoup.connect(url);
        Connection conHeader = conn.headers(header);
        HashMap<String, String> cookies = new HashMap<>();
        cookies.put("_ym_uid","171707630251817401");
        cookies.put("_ym_d","1717076302");
        cookies.put("over18","1");
        cookies.put("_ym_isad","2");
        cookies.put("cf_clearance","Ap9GiMT8IYzKe1WI673G3tnu77I4NGde1uYsZ9MqyDI-1717077016-1.0.1.1-9u.snyZDm2JX6WBe0GsJuUuL5Bc26MioEqlf.qmZcAAK6LNVV_np4CdJVeoU1kQazCKAJ8Emyen3rXv3Yz6w4w");
        cookies.put("_rucaptcha_session_id","ea9f156b851631a25bba42956eb52c42");
        cookies.put("remember_me_token","eyJfcmFpbHMiOnsibWVzc2FnZSI6IklrcE5OVTV2YUZGSFlWZERhR3BWV1ZkUlFqUTJJZz09IiwiZXhwIjoiMjAyNC0wNi0wNlQxNDowNTozNS4wMDBaIiwicHVyIjoiY29va2llLnJlbWVtYmVyX21lX3Rva2VuIn19--13fe257537cbac1d2038c7c01c4ff3ade7086ed0; _jdb_session=gcHqgSYLTJGeZ7qngsHX1oTRSJZMMsvbp1c8ca4cY6dqdNR2kLkclaVAKKi5sy62J7t8J81YUv%2B41RlaCpfrpgRm%2FrUo4tG7Eqs0uF6SRwWnyPSJNXljWIhT92dZLFRigaVYRS47JSbi3HxwPm90gnHbXQAKRGFX1%2BgZrdFpzjTwqgt9tBJo5wol4ioyyUJpZbXdWuKy0j2cfvv%2FZ%2F%2BwC21qMKHZPvI2zh99pKp69ylXc003TiyCH3vQ%2F82O3jad2dLDK3cmQnW3K838M9mziNSMWYFkkfXBCMapZfgLwKbm%2BTUHXiFPSPwCJPZ0T%2B5Tg2aso0OJ1SCciNDxeQaMKN2cd3aHtEVS%2FfjqG7YKy0aG8MFPVUxXjcbLUG3%2FaMM%2FVMo%3D--ruNuNs2eIO6MuBwA--iM77fVUqLLtJOb9yzpZjMQ%3D%3D");
        cookies.put("list_mode","h");
        cookies.put("theme","auto");
        cookies.put("locale","zh");









        try{
            Document doc = conHeader.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();

            Elements elements = doc.getElementsByClass("Po_topic_title");
            for (Element element : elements) {
                String inner_url = element.select("a").attr("href");


                Document inner_doc = Jsoup.connect(inner_url).timeout(Integer.MAX_VALUE).get();


                magnet_model model = new magnet_model();
                //获取标题
                String str = inner_doc.getElementsByAttributeValue("id", "title").text();

                model.setTitle(str);

                List<String> infobox = inner_doc.getElementsByClass("infobox").eachText();
                //获取番号
                if (infobox.get(0).indexOf(' ') == -1) {
                    model.setNum(infobox.get(0).substring(infobox.get(0).indexOf('：') + 1));
                } else {
                    model.setNum(infobox.get(0).substring(infobox.get(0).indexOf('：') + 1, infobox.get(0).indexOf(' ')));
                }
                System.out.println(model.getNum());

                //获取日期
                model.setDate(infobox.get(1).substring(infobox.get(1).indexOf('：') + 1));

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

                List<String> content_bt_url = inner_doc.getElementsByClass("content_bt_url").eachAttr("href");



                //获取磁链
//                Document javPage = Jsoup.connect("https://javdb.com/search?q=" + model.getNum()+"&f=all").timeout(Integer.MAX_VALUE).get();
                Document javPage = Jsoup.connect("https://javdb.com/search?q=" + model.getNum() + "&f=all").cookies(cookies).timeout(Integer.MAX_VALUE).get();

                String jav_inner = javPage.getElementsByClass("item").first().select("a").attr("href");

                Document javdb_inDoc = Jsoup.connect("https://javdb.com/" + jav_inner).timeout(Integer.MAX_VALUE).get();
                //获取画质与字幕、磁力链接
                int flag = 0;
                String magenet_noSub = null;
                Elements inner_elements = javdb_inDoc.getElementsByAttributeValue("class", "magnet-name column is-four-fifths");
                for (Element inner_ele : inner_elements) {
                    String inner_str = inner_ele.select("div").text();

                    if (inner_str.contains("字幕")) {
                        String magenet = inner_ele.select("a").attr("href");
                        if (magenet.contains(".torrent")) {
                            magenet = magenet.replace(".torrent", "");
                        }
                        flag = 1;

                        model.setMagenet(magenet);
                        model.setSubline("中文字幕");
                        model.setHD("高清");
                        break;
                    } else {
                        magenet_noSub = inner_ele.select("a").attr("href");
                        magenet_noSub = magenet_noSub.replace(".torrent", "");

                    }

                }
                if (flag == 0) {
                    model.setMagenet(magenet_noSub);
                    model.setSubline("无");
                    model.setHD("高清");

                }
                model_list.add(model);
                MySqlControl.executeInsert(model);


//

            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return model_list;

    }
    public static void downloadImages(String fileName, String ImageUrl){
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
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36");
            conn.setRequestProperty("User-Agent", "Mozilla/4.76");
            conn.setRequestProperty("Cookie","list_mode=h; theme=auto; locale=zh; _ym_uid=171707630251817401; _ym_d=1717076302; over18=1; _ym_isad=2; cf_clearance=Ap9GiMT8IYzKe1WI673G3tnu77I4NGde1uYsZ9MqyDI-1717077016-1.0.1.1-9u.snyZDm2JX6WBe0GsJuUuL5Bc26MioEqlf.qmZcAAK6LNVV_np4CdJVeoU1kQazCKAJ8Emyen3rXv3Yz6w4w; _jdb_session=u4ETQwHqRDNvHfmSlSxfZQB6XUJd%2BEBLnLlMVn1ZtS1SukifsPpdrG2RmoDyizgpKLcv%2F%2BlkWLjiflifkP0LdZf%2FaccjEFNd93B0b%2B3LVi7LndVysJCJiVJi%2FTpEr1ibulP9VyO9TSx3SXzQn%2FnZ%2F5mwD6ZyqsXtqIhzxRl2BiJsGXq87M7sLI6Wx5OIZBHSld5dLIBVk1GTzNEz%2B%2FF4QYmL%2FrORf2R%2BhJ4tp937mZOiqp6zKGNPSi68GA%2F5s2VMxmLTSOX1UBrgLE26tOrnKwe5laHpXgMuSS5R9cho%2FVhzLucgl9xn0cLD--LdW0gUg126G89iaP--6PGQ5z88iZ%2Bln4dDCNgcpw%3D%3D");
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







    static class Builder{
        //设置userAgent库;读者根据需求添加更多userAgent
        String[] userAgentStrs = {"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36"};
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
        String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";
        String acceptLanguage = "zh-cn,zh;q=0.5";
        String acceptEncoding = "gzip, deflate";
        String Cookie = "list_mode=h; theme=auto; locale=zh; _ym_uid=171707630251817401; _ym_d=1717076302; over18=1; _ym_isad=2; cf_clearance=Ap9GiMT8IYzKe1WI673G3tnu77I4NGde1uYsZ9MqyDI-1717077016-1.0.1.1-9u.snyZDm2JX6WBe0GsJuUuL5Bc26MioEqlf.qmZcAAK6LNVV_np4CdJVeoU1kQazCKAJ8Emyen3rXv3Yz6w4w; _rucaptcha_session_id=ea9f156b851631a25bba42956eb52c42; remember_me_token=eyJfcmFpbHMiOnsibWVzc2FnZSI6IklrcE5OVTV2YUZGSFlWZERhR3BWV1ZkUlFqUTJJZz09IiwiZXhwIjoiMjAyNC0wNi0wNlQxNDowNTozNS4wMDBaIiwicHVyIjoiY29va2llLnJlbWVtYmVyX21lX3Rva2VuIn19--13fe257537cbac1d2038c7c01c4ff3ade7086ed0; _jdb_session=gcHqgSYLTJGeZ7qngsHX1oTRSJZMMsvbp1c8ca4cY6dqdNR2kLkclaVAKKi5sy62J7t8J81YUv%2B41RlaCpfrpgRm%2FrUo4tG7Eqs0uF6SRwWnyPSJNXljWIhT92dZLFRigaVYRS47JSbi3HxwPm90gnHbXQAKRGFX1%2BgZrdFpzjTwqgt9tBJo5wol4ioyyUJpZbXdWuKy0j2cfvv%2FZ%2F%2BwC21qMKHZPvI2zh99pKp69ylXc003TiyCH3vQ%2F82O3jad2dLDK3cmQnW3K838M9mziNSMWYFkkfXBCMapZfgLwKbm%2BTUHXiFPSPwCJPZ0T%2B5Tg2aso0OJ1SCciNDxeQaMKN2cd3aHtEVS%2FfjqG7YKy0aG8MFPVUxXjcbLUG3%2FaMM%2FVMo%3D--ruNuNs2eIO6MuBwA--iM77fVUqLLtJOb9yzpZjMQ%3D%3D";
        String host;
    }
}
