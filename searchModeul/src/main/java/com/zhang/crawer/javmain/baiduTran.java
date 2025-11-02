package com.zhang.crawer.javmain;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zhang.crawer.javmain.baiduDemo.TransApi;

import java.util.Optional;

public class baiduTran {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private String appid;
    private String securityKey;
    public  String TranMain(String str) {
        TransApi api = new TransApi("20251019002478789", "JRoSborSHPYXlK9aSgNZ");
        String query = str;
        String json = api.getTransResult(query, "auto", "zh");
//        System.out.println(api.getTransResult(query, "auto", "zh"));
        // 1. 转对象
        JSONObject root = JSON.parseObject(json);

// 2. 取中文
        String dst = Optional.ofNullable(root.getJSONArray("trans_result"))
                .map(arr -> arr.getJSONObject(0))
                .map(obj -> obj.getString("dst"))
                .orElse("0");

        System.out.println(dst);
        return dst;

    }

}
