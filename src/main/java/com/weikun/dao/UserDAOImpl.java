package com.weikun.dao;

import com.weikun.db.DruidDB;
import com.weikun.vo.BBSUser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;

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
    public BBSUser login(BBSUser user) {
        BBSUser user1=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean flag=false;
        try {
            pstmt=conn.prepareStatement("select * from bbsuser where username=? and password=?  ");

            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            rs=pstmt.executeQuery();
            if(rs.next()){

                user1=new BBSUser();
                user1.setId(rs.getInt("id"));
                user1.setUsername(rs.getString("username"));
                user1.setPassword(rs.getString("password"));
            }


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
            return user1;

        }



    }

    @Override
    public boolean addUser(BBSUser user) {


        PreparedStatement pstmt=null;

        boolean flag=false;
        try {
            pstmt=conn.prepareStatement("insert into bbsuser (username,password,pic,pagenum) values(?,?,?,?); ");


            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            FileInputStream is=new FileInputStream(user.getPath());
            //blob
            pstmt.setBinaryStream(3,is,is.available());
            pstmt.setInt(4,user.getPagenum());

            flag=pstmt.executeUpdate()>0?true:false;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{


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

    @Override
    public byte[] queryPicByid(int id) {
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        byte[] buffer=null;
        try {
            pstmt=conn.prepareStatement("select * from bbsuser where id=?  ");

            pstmt.setInt(1,id);

            rs=pstmt.executeQuery();

            if(rs.next()){
                //读取blob
                Blob b=rs.getBlob("pic");//

                buffer=b.getBytes(1,(int)(b.length()));


            }

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


        }
        return buffer;
    }
}
