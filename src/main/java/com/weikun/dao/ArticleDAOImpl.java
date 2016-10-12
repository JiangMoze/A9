package com.weikun.dao;

import com.weikun.db.DBCPDB;
import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class ArticleDAOImpl implements IArticleDAO {
    private Connection conn;
    public ArticleDAOImpl(){

        conn= DBCPDB.getDBCPConnection();
    }

    public static void main(String[] args) {
        new ArticleDAOImpl().queryAll(1,999);


    }

    @Override
    public PageBean queryAll(int curPage,int usrid) {
        CallableStatement cs=null;
        ResultSet rs=null;
        ArrayList<Article> list=new ArrayList<Article>();
        PageBean pb=new PageBean();
        try {
            /*

            	in curPage int,
                in usrid int,
                out maxPage int,
                out maxRowCount int,
                out rowsPerPage int

             */
            String sql=" call q4(?,?,?,?,?) ";
            cs=conn.prepareCall(sql);
            cs.setInt(1,curPage);
            cs.setInt(2,usrid);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.INTEGER);

            boolean flag=cs.execute();

            while(flag){

                rs=cs.getResultSet();
                pb.setCurPage(curPage);//当前页
                pb.setMaxPage(cs.getInt(3));
                pb.setMaxRowCount(cs.getInt(4));
                pb.setRowsPerPage(cs.getInt(5));

                while(rs.next()){
                    Article a=new Article();
                    a.setId(rs.getInt("id"));
                    a.setTitle(rs.getString("title"));
                    a.setContent(rs.getString("content"));
                    a.setDatetime(rs.getString("datetime"));

                    list.add(a);

                }
                pb.setData(list);//每页的数据
                flag=cs.getMoreResults();//是否有更多的结果集
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(cs!=null){
                try {
                    cs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return pb;
    }
}
