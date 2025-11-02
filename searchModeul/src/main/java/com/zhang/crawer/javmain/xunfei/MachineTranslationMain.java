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
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 1.机器翻译2.0,请填写在讯飞开放平台-控制台-对应能力页面获取的APPID、APISecret、APIKey。
 * 2.目前仅支持中文与其他语种的互译，不包含中文的两个语种之间不能直接翻译。
 * 3.翻译文本不能超过5000个字符，即汉语不超过15000个字节，英文不超过5000个字节。
 * 4.此接口调用返回时长上有优化、通过个性化术语资源使用可以做到词语个性化翻译、后面会支持更多的翻译语种。
 */
public class MachineTranslationMain {


    private final String appId, apiSecret, apiKey, from, to, text;
    private final String requestUrl = "https://itrans.xf-yun.com/v1/its";
    private final Gson gson = new Gson();

    MachineTranslationMain(String appId, String apiSecret, String apiKey,
                           String from, String to, String text) {
        this.appId = appId;
        this.apiSecret = apiSecret;
        this.apiKey = apiKey;
        this.from = from;
        this.to = to;
        this.text = text;
    }

    String doRequest() throws Exception {
        URL url = new URL(buildRequestUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        String param = buildParam();
        try (OutputStream out = conn.getOutputStream()) {
            out.write(param.getBytes(StandardCharsets.UTF_8));
        }

        InputStream is = conn.getResponseCode() == HttpURLConnection.HTTP_OK
                ? conn.getInputStream() : conn.getErrorStream();
        String resp = readAll(is);
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("http error:" + resp);
        }
        return resp;
    }

    private String buildRequestUrl() throws Exception {
        URL url = new URL(requestUrl);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = sdf.format(new Date());
        String host = url.getHost();
        StringBuilder signBuilder = new StringBuilder("host: ").append(host).append('\n')
                .append("date: ").append(date).append('\n')
                .append("POST ").append(url.getPath()).append(" HTTP/1.1");

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        String sha = Base64.getEncoder().encodeToString(mac.doFinal(signBuilder.toString().getBytes(StandardCharsets.UTF_8)));

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                apiKey, "hmac-sha256", "host date request-line", sha);
        String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8));

        return String.format("%s?authorization=%s&host=%s&date=%s",
                requestUrl,
                URLEncoder.encode(authBase, "UTF-8"),
                URLEncoder.encode(host, "UTF-8"),
                URLEncoder.encode(date, "UTF-8"));
    }

    private String buildParam() {
        return "{" +
                "\"header\":{\"app_id\":\"" + appId + "\",\"status\":3}," +
                "\"parameter\":{\"its\":{\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"result\":{}}}," +
                "\"payload\":{\"input_data\":{\"encoding\":\"utf8\",\"status\":3,\"text\":\""
                + Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8)) + "\"}}}";
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

    // JSON解析
    class JsonParse {
        Payload payload;
    }

    class Payload {
        Result result;
    }

    class Result {
        String text;
    }
}