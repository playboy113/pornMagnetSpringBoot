package com.zhang.crawer.db;

import org.apache.commons.dbcp2.BasicDataSource;

public class MyDataSource {
    public static javax.sql.DataSource getDataSource(String connectURI){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setUrl(connectURI);
        return ds;
    }
}
