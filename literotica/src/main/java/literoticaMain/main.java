package literoticaMain;

import commons.setHeader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

public class main {
    public static void main(String[] args) throws IOException {
        String url = "https://www.literotica.com/top/most-read-erotic-stories/alltime/?page=";
        //设置请求头
        Builder builder = new Builder();
        setHeader.setUp();
        builder.host = "https://www.literotica.com/";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);
        for (int i=1;i<10;i++){
            selectPage(url+i, header);
        }

    }

    private static void selectPage(String url, Map<String, String> header) throws IOException {
        Connection conn = Jsoup.connect(url);
        Connection conHeader = conn.headers(header);
        Document doc = conHeader.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
        Elements elements = doc.getElementsByClass("mcol");
        int list=0;
        for (Element ele:elements){
            String page_attr = ele.select("a").attr("href");
            String title = ele.select("a").text();
            System.out.println(title);
            selectTitle(page_attr, title,list);
            list++;


        }
    }

    private static void selectTitle(String page_attr, String title,int list) throws IOException {

        //獲取页面代码

        if (page_attr.contains("https://www.literotica.com/")){
            Document doc_pageCount = Jsoup.connect(page_attr).timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
            String page_count="1";
            if (doc_pageCount.getElementsByClass("l_bJ").last()!=null){
                page_count = doc_pageCount.getElementsByClass("l_bJ").last().text();
            }




            title.replace("\"","");
            String filePath = "literotica/articles/"+list+ title +".txt";
            try{
                FileWriter fileWriter = new FileWriter(filePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for (int i=1;i<=Integer.valueOf(page_count);i++){
                    Document inner_doc = Jsoup.connect(page_attr+"?page="+i).timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
                    Elements aa_hts = inner_doc.getElementsByClass("aa_ht");
                    for (Element aa_ht:aa_hts){
                        Elements pharses = aa_ht.select("p");
                        for (Element ph:pharses){
                            System.out.println(ph.text());
                            bufferedWriter.write(ph.text()+"\n");
                        }

                    }
                }

                bufferedWriter.close();
                fileWriter.close();
            }catch(IOException e){
                e.printStackTrace();

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
