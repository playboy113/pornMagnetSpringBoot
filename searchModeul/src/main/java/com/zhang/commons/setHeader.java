package com.zhang.commons;

public class setHeader {
    public static void setUp(){
        String proxyHost = "127.0.0.1";
        String proxyPort = "7897";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);
    }
}
