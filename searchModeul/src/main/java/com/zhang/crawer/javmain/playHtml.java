package com.zhang.crawer.javmain;

import com.zhang.commons.setHeader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class playHtml {
    public String playingHtml(String num){

        playHtml.Builder builder = new playHtml.Builder();
        setHeader.setUp();
        builder.host = "https://missav.ws/cn/";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);
        header.put("Cookie", builder.Cookie);

        Connection conn = Jsoup.connect("https://missav.ws/cn/search/"+num);
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
        HashMap<String,String> videosHtml = new HashMap();
        try {
            Document doc = conHeader.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
            Elements elements = doc.getElementsByClass("relative aspect-w-16 aspect-h-9 rounded overflow-hidden shadow-lg");
            for (Element ele:elements){
                String url = ele.select("a").attr("href");
                String text = ele.select("a").text();
                videosHtml.put(text,url);



            }

        } catch (Exception e) {
            // 捕获算术异常或空指针异常
            System.err.println("Error occurred: " + e.getMessage() + ". Skipping this iteration.");
            e.printStackTrace();
        }
        String finalURL = null;

        for (String key:videosHtml.keySet()){
            if(key.contains("中文字幕") ){
                finalURL = videosHtml.get(key);
                break;

            } else if (key.contains("无码影片")) {
                finalURL = videosHtml.get(key);


            }else{
                finalURL = videosHtml.get(key);
            }
        }

        return finalURL;


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
