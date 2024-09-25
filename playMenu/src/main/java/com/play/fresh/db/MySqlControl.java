package com.play.fresh.db;


import com.play.fresh.entity.videosLocate;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ComponentScan
public class MySqlControl {
    static javax.sql.DataSource ds =  MyDataSource.getDataSource("jdbc:mysql://127.0.0.1:3306/magnet?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false");
    static QueryRunner qr = new QueryRunner(ds);
    static int num = 1;

    public static void executeInsert(List<videosLocate> data){
        Object[][] params = new Object[data.size()][2];
        for (int i =0;i< params.length;i++){
            params[i][0] = data.get(i).getNum();
            params[i][1] = data.get(i).getLocate();

        }

        try {
            qr.execute("delete from magnet_videos_locate");
            qr.batch("insert into magnet_videos_locate(num,locate) values (?,?)",params);
//            qr.execute("delete from magnet_db where magnet in (\n" +
//                    "    select\n" +
//                    "       t.magnet\n" +
//                    "    from (\n" +
//                    "         select\n" +
//                    "        magnet\n" +
//                    "        from magnet_db\n" +
//                    "        group by magnet\n" +
//                    "        having count(1)>1\n" +
//                    "             ) t\n" +
//                    "    ) and magnet not in (\n" +
//                    "select\n" +
//                    "dt.minMagnet\n" +
//                    "from\n" +
//                    "(select min(num) as minMagnet from magnet_db\n" +
//                    "group by magnet\n" +
//                    "having count(1) >1) dt)");

            //System.out.println("成功插入数据"+data.size()+"条");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void executeInsert(Map<String,String> data){
        Object[][] params = new Object[data.size()][2];
        Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
        int count=0;
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            params[count][0]=entry.getKey();
            params[count][1]=entry.getValue();
            count++;

        }





        try {
            qr.execute("delete from magnet_videos_locate");
            qr.batch("insert into magnet_videos_locate(num,locate) values (?,?)",params);

//            qr.execute("delete from magnet_db where magnet in (\n" +
//                    "    select\n" +
//                    "       t.magnet\n" +
//                    "    from (\n" +
//                    "         select\n" +
//                    "        magnet\n" +
//                    "        from magnet_db\n" +
//                    "        group by magnet\n" +
//                    "        having count(1)>1\n" +
//                    "             ) t\n" +
//                    "    ) and magnet not in (\n" +
//                    "select\n" +
//                    "dt.minMagnet\n" +
//                    "from\n" +
//                    "(select min(num) as minMagnet from magnet_db\n" +
//                    "group by magnet\n" +
//                    "having count(1) >1) dt)");

            //System.out.println("成功插入数据"+data.size()+"条");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void insertTypes(String num,String[] splits) throws SQLException {
        String[][] params = new String[1][2];
        params[0][0]=num;
        if(splits == null){
            params[0][1]=null;
            qr.batch("insert into magnet_type2(num,types) values(?,?)",params);
        }else{
            for (int i=0;i< splits.length;i++){
                params[0][1] = splits[i];
                qr.batch("insert into magnet_type2(num,types) values(?,?)",params);

            }
        }


        qr.update("delete from magnet_types where num=''");
    }

    public static void insertActress(String num, String[] actressArr) throws SQLException {
        String[][] params = new String[1][2];
        params[0][0] = num;
        for (int i=0;i<actressArr.length;i++){
            params[0][1] = actressArr[i];
            qr.batch("insert into magnet_actress(num,actress) values(?,?)",params);
        }
        qr.update("delete from magnet_actress where num=''");

    }
}
