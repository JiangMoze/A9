package com.weikun.dao;

import com.weikun.db.DruidDB;
import com.weikun.vo.BBSUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/10/9.
 */
public class UserDAOImpl implements  IUserDAO {
    private Connection conn=null;
    public UserDAOImpl(){

        conn= DruidDB.getConnection();
    }
    /**
     *
     * @param user:传递用户名和密码，
     * @return true：登录成功
     *         false：登录失败
     */
    public boolean login(BBSUser user) {
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean flag=false;
        try {
            pstmt=conn.prepareStatement("select * from bbsuser where username=? and password=?  ");

            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            rs=pstmt.executeQuery();
            flag=rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs!=null){
                    rs.close();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(pstmt!=null){
                    pstmt.close();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return flag;

        }



    }
}
