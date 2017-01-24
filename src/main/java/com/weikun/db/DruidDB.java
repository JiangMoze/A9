package com.weikun.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description: Druid连接池
 * User: Moze
 * Date: 2017-01-09
 * Time: 19:47
 */
public class DruidDB {
    private static Connection conn;
    private static DataSource ds;

    static {
        Properties pro = new Properties();
        try {
            //加载驱动
            pro.load(DruidDB.class.getClassLoader().getResourceAsStream("druid.ini"));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
