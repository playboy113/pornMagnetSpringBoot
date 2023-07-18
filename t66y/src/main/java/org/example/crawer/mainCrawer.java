package org.example.crawer;

import org.apache.tomcat.util.buf.Utf8Decoder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

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
        builder.host = "https://t66y.com/";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);

        for (int i=21;i<40;i++){
            System.out.println("开始下载第"+i+"个网页");
            Connection connect = Jsoup.connect("https://t66y.com/thread0806.php?fid=16&search=&page="+i);
            Connection headers = connect.headers(header);
            try{
                Document document = headers.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
                Elements elements = document.getElementsByClass("tr3 t_one tac");
                int num=1;
                for (Element ele:elements){
                    String attr = ele.getElementsByAttributeValue("target", "_blank").attr("href");
                    downloadDir("https://t66y.com/"+attr,header);
                    //System.out.println("开始下载第"+num+"个网页");
                    num++;
                }
            }catch(IOException e){
                e.printStackTrace();
            }


        }



    }

    private static void downloadDir(String url,Map<String, String> header) throws IOException {
        Connection connect = Jsoup.connect(url);
        Connection headers = connect.headers(header);
        Document document = headers.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
        String h4 = document.getElementsByTag("h4").text();
        String path= "D:\\pornDocs\\"+h4;
        File folder = new File(path);
        if (!folder.exists()) { // 检查文件夹是否存在
            boolean created = folder.mkdirs(); // 创建文件夹（包括父文件夹）
            if (created) {
                //System.out.println("文件夹创建成功！");
            } else {
                //System.out.println("文件夹创建失败。");
            }
        } else {
            System.out.println("文件夹已存在。");
        }
        //System.out.println(h4);
        Elements elementsByClass = document.getElementsByAttribute("ess-data");
        int flag=1;
        for (Element ele:elementsByClass){
            String attr = ele.attr("ess-data");
            downloadImages(flag+"",attr,path);
            flag++;
        }
    }

    public static void downloadImages(String fileName,String ImageUrl,String path){
        URL urlObj = null;
        URLConnection conn=null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        //String savePath = "D:\\github\\pornMagnetSpringBoot\\searchModeul\\src\\main\\resources\\images";
        String savePath = path+"\\";
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
