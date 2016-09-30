package com.weikun.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/9/30.
 */
public class DruidDB {
    private static Connection conn;
    private static DataSource ds;
    static {
        Properties pro=new Properties();
        try {
            pro.load(DruidDB.class.getClassLoader().getResourceAsStream("druid.ini"));

            ds=DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection getConnection(){

        try {
            conn=ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        System.out.print(getConnection());
    }
}
