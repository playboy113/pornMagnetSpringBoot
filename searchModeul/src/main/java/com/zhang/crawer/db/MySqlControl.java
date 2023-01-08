package com.zhang.crawer.db;

import com.zhang.crawer.entity.magnet_model;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.util.List;

public class MySqlControl {
    static javax.sql.DataSource ds =  MyDataSource.getDataSource("jdbc:mysql://127.0.0.1:3306/magnet?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false");
    static QueryRunner qr = new QueryRunner(ds);
    static int num = 1;

    public static void executeInsert(List<magnet_model> data){
        Object[][] params = new Object[data.size()][9];
        for (int i =0;i< params.length;i++){
            params[i][0] = data.get(i).getTitle();
            params[i][1] = data.get(i).getActress();
            params[i][2] = data.get(i).getSubline();
            params[i][3] = data.get(i).getHD();
            params[i][4] = data.get(i).getMagenet();
            params[i][5] = data.get(i).getNum();
            params[i][6] = data.get(i).getTypes();
            params[i][7] = data.get(i).getDate();
            params[i][8] = data.get(i).getProducer();
        }

        try {
            qr.batch("insert into magnet_db(title,actress,subline,HD,magnet,num,types,date,producer) values (?,?,?,?,?,?,?,?,?)",params);
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

            System.out.println("成功插入数据"+data.size()+"条");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void executeInsert(magnet_model magnet_model){

            Object[][] params = new Object[1][9];
            params[0][0] = magnet_model.getTitle();
            params[0][1] = magnet_model.getActress();
            params[0][2] = magnet_model.getSubline();
            params[0][3] = magnet_model.getHD();
            params[0][4] = magnet_model.getMagenet();
            params[0][5] = magnet_model.getNum();
            params[0][6] = magnet_model.getTypes();
            params[0][7] = magnet_model.getDate();
            params[0][8] = magnet_model.getProducer();


        try {
            qr.batch("insert into magnet_db(title,actress,subline,HD,magnet,num,types,date,producer) values (?,?,?,?,?,?,?,?,?)",params);
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

            System.out.println("成功插入数据"+magnet_model.getTitle()+"文件数量"+num+"条");
            num++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertTypes(String num,String[] splits) throws SQLException {
        String[][] params = new String[1][16];
        params[0][0]=num;
        if (splits.length<=16){
            for (int i=0;i< splits.length;i++){
                params[0][i+1]=splits[i];
            }
        }else{
            for (int i=0;i<16;i++){
                params[0][i+1]=splits[i];
            }
        }

        qr.batch("insert into magnet_types(num,type1,type2,type3,type4,type5,type6,type7,type8,type9,type10,type11,type12,type13,type14,type15) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",params);
        qr.update("delete from magnet_types where num=''");
    }
}
