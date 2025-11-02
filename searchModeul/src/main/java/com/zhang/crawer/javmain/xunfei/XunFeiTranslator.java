package com.zhang.crawer.javmain.xunfei;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 讯飞机器翻译 2.0 工具类
 * 依赖：fastjson、gson（与原代码保持一致）
 */
public final class XunFeiTranslator {

    public XunFeiTranslator() {}

    /**
     * 同步翻译
     *
     *
     * @param text      待翻译文本（≤5000 字符）
     * @return 翻译后 text 字段对应的 JSONObject
     * @throws Exception 网络、鉴权、业务异常等
     */
    public  String translate(String text) throws Exception {

        String resp = new ApiCaller( text).doRequest();
        JsonParse json = new Gson().fromJson(resp, JsonParse.class);
        String base64Text = json.payload.result.text;
        String raw = new String(Base64.getDecoder().decode(base64Text), StandardCharsets.UTF_8);
//        System.out.println(JSON.parseObject(raw).getJSONObject("trans_result")
//                .getString("dst"));

        return JSON.parseObject(raw).getJSONObject("trans_result")
                .getString("dst");

//        return Optional.ofNullable(
//                        com.alibaba.fastjson.JSONObject.parseObject(raw)
//                                .getJSONArray("src"))
//                .map(arr -> arr.getString(0))
//                .orElse("0");
    }

    /* ===================== 以下与原类基本一致，仅去掉 static 硬编码 ===================== */
    private static class ApiCaller {
        private final String appId, apiSecret, apiKey,RES_ID, from, to, text;
        private final String requestUrl = "https://itrans.xf-yun.com/v1/its";
        private final Gson gson = new Gson();

        ApiCaller(String text) {
            this.appId = "16d19e2a";
            this.apiSecret = "YzU2MmU2ZWI2MGU1NTU4YzgwOGQwOWJh";
            this.apiKey = "b2fa7e1de67945942064d9eb51e148b9";
            this.from = "ja";
            this.RES_ID ="its_ja_cn_word";
            this.to = "cn";
            this.text = text;
        }

        String doRequest() throws Exception {
            URL realUrl = new URL(buildRequestUrl());
            URLConnection connection = realUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-type","application/json");
            OutputStream out = httpURLConnection.getOutputStream();
            String params = buildParam();
            System.out.println("params=>"+params.replaceAll(" ",""));
            out.write(params.getBytes());
            out.flush();
            InputStream is = null;
            try{
                is = httpURLConnection.getInputStream();
            }catch (Exception e){
                is = httpURLConnection.getErrorStream();
                throw new Exception("make request error:"+"code is "+httpURLConnection.getResponseMessage()+readAll(is));
            }
            return readAll(is);
        }

        private String buildRequestUrl() throws Exception {
            URL url = null;
            // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
            String  httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://","https://" );
            try {
                url = new URL(httpRequestUrl);
                //获取当前日期并格式化
                SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                String date = format.format(new Date());
                //String date="Thu, 18 Nov 2021 03:05:18 GMT";
                String host = url.getHost();
           /* if (url.getPort()!=80 && url.getPort() !=443){
                host = host +":"+String.valueOf(url.getPort());
            }*/
                StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                        append("date: ").append(date).append("\n").//
                        append("POST ").append(url.getPath()).append(" HTTP/1.1");
                Charset charset = Charset.forName("UTF-8");
                Mac mac = Mac.getInstance("hmacsha256");
                SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
                mac.init(spec);
                byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
                String sha = Base64.getEncoder().encodeToString(hexDigits);
                //System.out.println(sha);
                String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
                String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
                return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));
            } catch (Exception e) {
                throw new RuntimeException("assemble requestUrl error:"+e.getMessage());
            }
        }

        private String buildParam() {
            String param = "{"+
                    "    \"header\": {"+
                    "        \"app_id\": \""+appId+"\","+
                    "        \"status\": 3,"+
                    "        \"res_id\": \""+RES_ID+"\""+
                    "    },"+
                    "    \"parameter\": {"+
                    "        \"its\": {"+
                    "            \"from\": \""+from+"\","+
                    "            \"to\": \""+to+"\","+
                    "            \"result\": {}"+
                    "        }"+
                    "    },"+
                    "    \"payload\": {"+
                    "        \"input_data\": {"+
                    "            \"encoding\": \"utf8\","+
                    "            \"status\": 3,"+
                    "            \"text\": \""+Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8))+"\""+
                    "        }"+
                    "    }"+
                    "}";
            return param;
        }

        private String readAll(InputStream is) throws IOException {
            byte[] buf = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len;
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, StandardCharsets.UTF_8));
            }
            return sb.toString();
        }
    }

    /* ===================== JSON 解析辅助类 ===================== */
    private static class JsonParse {
        Payload payload;
    }
    private static class Payload {
        Result result;
    }
    private static class Result {
        String text;
    }
}