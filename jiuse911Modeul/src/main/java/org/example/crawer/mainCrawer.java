package org.example.crawer;

import org.apache.tomcat.util.buf.Utf8Decoder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class mainCrawer {
    public static void main(String[] args) throws IOException {
        String proxyHost = "127.0.0.1";
        String proxyPort = "7890";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);

        Builder builder = new Builder();
        builder.host = "https://91porny.com/";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);

        for (int i=1;i<15;i++){
            Connection connect = Jsoup.connect("https://91porny.com/search?keywords=%E5%BE%A1%E5%A7%90&view=desc&page="+i);
            Connection headers = connect.headers(header);
            Document document = headers.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
            Elements elements = document.getElementsByClass("colVideoList");
            for (Element ele:elements){
                String attr = ele.getElementsByTag("a").attr("href");
                try{
                    Document inner_doc = Jsoup.connect("https://91porny.com" + attr).headers(header).timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
                    //视频标题提取
                    String title = inner_doc.getElementsByClass("container-title py-3 mb-0").text();

                    String video_url = inner_doc.getElementsByClass("videoPlayContainer").first().getElementById("video-play").attr("data-src");
                    String[] arrExec = new String[]{"D:\\java\\codes\\images\\N_m3u8DL-CLI_v3.0.2.exe",""+video_url,"--saveName",title,"--workDir","D:\\java\\codes\\images"};
                    //String[] arrExec = new String[]{"cmd /c dir"};
                    //String[] arrExec = new String[]{"ipconfig"};
                    Process process = Runtime.getRuntime().exec(arrExec);
                    System.out.println("准备开始下载："+title);
                    try (InputStream fis = process.getInputStream();
                         InputStreamReader isr = new InputStreamReader(fis);
                         BufferedReader br = new BufferedReader(isr)) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            //System.out.println(line);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

        }



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
