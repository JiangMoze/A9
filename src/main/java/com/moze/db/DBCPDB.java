package com.moze.db;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * Description: DBCP连接池
 * User: Moze
 * Date: 2017-01-09
 * Time: 19:51
 */
public class DBCPDB {
    private static DataSource ds = null;
    private static Connection conn = null;

    static {
        Properties pro = new Properties();
        try {
            pro.load(DBCPDB.class.getClassLoader().getResourceAsStream("dbcp.ini"));
            ds = BasicDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过链接池获得连接
     *
     * @return connection对象
     */
    public static Connection getDBCPConnection() {
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
