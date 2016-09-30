package com.weikun.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/9/30.
 */
public class C3P0DB {
    private static Connection conn;
    private static ComboPooledDataSource ds=null;
    static{
        ds=new ComboPooledDataSource("mysql");



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
