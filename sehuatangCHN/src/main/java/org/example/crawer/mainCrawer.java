package org.example.crawer;

import org.apache.tomcat.util.buf.Utf8Decoder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

public class mainCrawer {
    public static String proxyHost = "127.0.0.1";
    public static String proxyPort = "7890";

    public static void main(String[] args) throws IOException {

        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);

        Builder builder = new Builder();
        builder.host = "https://sehuatang.net/";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);


        int start=1;
        int end =30;
        String keyWords="调教";
        List<String> magnetList;
        for(int i=start;i<end;i++){
            magnetList = new ArrayList<>();
            Connection connect = Jsoup.connect("https://t66y.com/thread0806.php?fid=25&search=&page="+i);
            Connection headers = connect.headers(header);
            Document document = headers.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
            Elements listPages = document.getElementsByClass("tr3 t_one tac");
            for (Element ele:listPages){
                String attr = ele.getElementsByAttributeValue("target", "_blank").attr("href");
                String title = ele.getElementsByAttributeValue("target", "_blank").text();

                //System.out.println("https://t66y.com/"+attr);

                if(title.contains(keyWords)){
                    System.out.println(title);
                    String magnet = htmlPage(header, "https://t66y.com/" + attr);
                    magnetList.add(magnet);

                }


            }
            saveMagnet(magnetList);
            System.out.println("第"+i+"页已经下载完毕");


        }



    }
    //进入详情页
    private static String htmlPage(Map<String, String> header,String htmlUrl) throws IOException {
        Connection connect = Jsoup.connect(htmlUrl);
        Connection headers = connect.headers(header);
        Document document = headers.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
        Elements style = document.getElementsByAttributeValue("style", "cursor:pointer;color:#008000;");
        String s = rmdownPage(style.text());
        //System.out.println(s);
        return  s;
    }
    //进入下载页
    private static String rmdownPage(String rmdownUrl) {
        System.setProperty("webdriver.chrome.driver", "Downloads/chromedriver_win32/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

            driver.get(rmdownUrl);

            WebElement element;
            String secondLine = null;
        try{
                element =driver.findElement(By.xpath("//*[@class='list']"));


                String line = element.getText();
                int newlineIndex = line.indexOf('\n');

                // 提取第一行字符串
                String firstLine = line.substring(0, newlineIndex);

                // 输出结果

            secondLine = firstLine.replace("Code: ", "magnet:?xt=urn:btih:");
                //System.out.println(element.getText());
                //System.out.println(secondLine);
            }catch (Exception e){
                e.printStackTrace();
            }
        driver.close();



        return  secondLine;






    }
    private static void saveMagnet(List<String> list) throws IOException {

        String OutputFileName = "Downloads/output.txt";


        BufferedWriter writer = new BufferedWriter(new FileWriter(OutputFileName,true));

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            writer.write(iterator.next());
            writer.newLine();
        }



        writer.close();
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
