package com.zhang.crawer.javmain;

import com.zhang.crawer.db.MySqlControl;
import com.zhang.crawer.entity.magnet_model;
import com.zhang.crawer.javmain.xunfei.XunFeiTranslator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class translateAPI {
    public String strTran(String str){
        return "str";
    }

    public static void main(String[] args) throws Exception {
//        String title = " 【抽選でサイン入り衣装プレゼント実施中】俺の従順ペット候補生 06 涼森れむ【MGSだけのおまけ映像付き+10分";
        FanyiV3Demo fanyiV3Demo = new FanyiV3Demo();
        baiduTran baiduTran = new baiduTran();
        XunFeiTranslator xunFeiTranslator = new XunFeiTranslator();
        tencentTran tencentTran = new tencentTran();
//        System.out.println(fanyiV3Demo.tranStr(title));
        MySqlControl mySqlControl = new MySqlControl();
        List<magnet_model> allList = mySqlControl.findAllEmpty();
        for (magnet_model magnet_model : allList) {
            String title = magnet_model.getTitle();
            String num = magnet_model.getNum();
            System.out.println("正在处理:"+num);
//有道翻译
//            String tranRet = fanyiV3Demo.tranStr(title);
//            System.out.println(tranRet);
//            mySqlControl.innsertChnTitle(num,tranRet);
//            if (!tranRet.equals("0")){
//                mySqlControl.innsertTitle(num,title+"\n"+fanyiV3Demo.tranStr(title));
//            }
//            百度翻译

//            String tranRet = baiduTran.TranMain(title);
//            mySqlControl.innsertChnTitle(num,tranRet);
//            if (!tranRet.equals("0")) {
//                mySqlControl.innsertTitle(num, title + "\n" + tranRet);
//            }
//            讯飞翻译
//            if(title.length() <= 300){
//                String tranRet = xunFeiTranslator.translate(title);
//                mySqlControl.innsertChnTitle(num,tranRet);
//                if (!tranRet.equals("0")) {
//                    mySqlControl.innsertTitle(num, title + "\n" + tranRet);
//                }
//                System.out.println(tranRet);
//
//            }
//            百度翻译
            if(title.length() <= 200){
                String tranRet = tencentTran.mainTran(title);
                mySqlControl.innsertChnTitle(num,tranRet);
                if (!tranRet.equals("0")) {
                    mySqlControl.innsertTitle(num, title + "\n" + tranRet);
                }
                System.out.println(tranRet);

            }



        }


    }
}
