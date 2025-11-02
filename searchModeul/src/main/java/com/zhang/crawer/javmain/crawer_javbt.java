package com.zhang.crawer.javmain;

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

import static com.zhang.crawer.javmain.downloadCover.downloadImages;

public class crawer_javbt {
    public ArrayList<magnet_model> javbt(String url) throws IOException, URISyntaxException {

        ArrayList<magnet_model> model_list = new ArrayList<>();
        //设置请求头
        Builder builder = new Builder();
        setHeader.setUp();
        builder.host = "https://freejavbt.com/";
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
        try {
            Document doc = conHeader.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
            Elements elements = doc.getElementsByClass("card");
            for (Element element : elements) {
                String inner_url = element.select("a").attr("href");
                try {

                    Document inner_doc = Jsoup.connect(inner_url).timeout(Integer.MAX_VALUE).get();
                    //Document inner_doc = Jsoup.connect("https://javdb.com/v/OX2rMB").timeout(Integer.MAX_VALUE).get();
                    magnet_model model = new magnet_model();
                    //获取番号
                    model.setNum(inner_doc.getElementsByAttributeValue("class", "col-12 col-xl-12 mx-auto mb-3 px-1").select("h1").text().trim().substring(0, inner_doc.getElementsByAttributeValue("class", "col-12 col-xl-12 mx-auto mb-3 px-1").select("h1").text().trim().indexOf(" ")));
                    System.out.println(inner_doc.getElementsByAttributeValue("class", "col-12 col-xl-12 mx-auto mb-3 px-1").select("h1").text().trim().substring(0, inner_doc.getElementsByAttributeValue("class", "col-12 col-xl-12 mx-auto mb-3 px-1").select("h1").text().trim().indexOf(" ")));
                    //获取标题
                    model.setTitle(inner_doc.getElementsByAttributeValue("class", "col-12 col-xl-12 mx-auto mb-3 px-1").select("h1").text().trim().substring(inner_doc.getElementsByAttributeValue("class", "col-12 col-xl-12 mx-auto mb-3 px-1").select("h1").text().trim().indexOf(" ") + 1));

                    //

                    Connection connDB = Jsoup.connect("https://javdb.com/search?q=" + model.getNum() + "&f=all");
                    Connection headersDB = connDB.headers(header);
                    try {
                        Document javPage = headersDB.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();

                        String jav_inner = javPage.getElementsByClass("item").select("a").attr("href");


                        Connection connDBinner = Jsoup.connect("https://javdb.com/" + jav_inner);
                        Connection headersDBinner = connDBinner.headers(header);

                        try {
                            Document javdb_inDoc = headersDBinner.timeout(Integer.MAX_VALUE).ignoreContentType(true).ignoreHttpErrors(true).get();
                            String[] typesArr = null;
                            String[] actressArr = null;
                            //获取演员与類別以及片商
                            Elements dess = javdb_inDoc.getElementsByClass("panel-block");
                            for (Element des : dess) {
                                if (des.select("strong").text().contains("演員")) {
                                    model.setActress(des.select("a").text());

                                    String[] newActressArr = des.select("a").text().split(" ");
                                    actressArr = new String[newActressArr.length];
                                    for (int i = 0; i < newActressArr.length; i++) {
                                        actressArr[i] = newActressArr[i];
                                    }


                                } else if (des.select("strong").text().contains("類別")) {
                                    model.setTypes(des.select("a").text());
                                    String[] newTypesArr = des.select("a").text().split(" ");
                                    typesArr = new String[newTypesArr.length];
                                    for (int i = 0; i < newTypesArr.length; i++) {
                                        typesArr[i] = newTypesArr[i];
                                    }


                                } else if (des.select("strong").text().contains("日期")) {
                                    model.setDate(des.select("span").text());
                                } else if (des.select("strong").text().contains("片商")) {
                                    model.setProducer(des.select("span").text());
                                } else if (des.select("strong").text().contains("系列")) {
                                    model.setSeries(des.select("span").text());
                                }
                            }

                            //获取封面图片并下载
                            String coverImg = javdb_inDoc.getElementsByAttributeValue("class", "video-cover").attr("src");
                            downloadImages(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text(), coverImg);

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
                        } catch (Exception e) {
                            // 捕获算术异常或空指针异常
                            System.err.println("Error occurred: " + e.getMessage() + ". Skipping this iteration.");
                            continue;
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model_list;
    }


    class Builder {
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
