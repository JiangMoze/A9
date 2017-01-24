package com.weikun.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description: C3P0连接池
 * User: Moze
 * Date: 2017-01-09
 * Time: 19:57
 */
public class C3P0DB {
    private static Connection conn;
    private static ComboPooledDataSource ds = null;

    static {
        ds = new ComboPooledDataSource("mysql");
    }

    /**
     * 通过链接池获得连接
     *
     * @return connection对象
     */
    public static Connection getConnection() {
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Test
    public void getConnectionTest() {
        try {
            conn = ds.getConnection();
            System.out.print(conn.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
