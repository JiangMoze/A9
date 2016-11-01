package com.weikun.dao;

import com.weikun.db.DBCPDB;
import com.weikun.vo.Article;
import com.weikun.vo.BBSUser;
import com.weikun.vo.PageBean;
import com.weikun.vo.ReArticle;

import java.io.StringReader;
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
                    BBSUser user=new BBSUser();

                    a.setId(rs.getInt("id"));
                    a.setTitle(rs.getString("title"));
                    a.setContent(rs.getString("content"));
                    a.setDatetime(rs.getString("datetime"));
                    user.setId(rs.getInt("userid"));

                    a.setUser(user);
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

    @Override
    public boolean addArticle(Article a) {
        PreparedStatement pstmt=null;
        boolean flag=false;
        try {                                             //longtext:clob
            String sql="insert into article(rootid,title,content,datetime,userid) values(?,?,?,now(),?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,a.getRootid());
            pstmt.setString(2,a.getTitle());
            pstmt.setInt(4,a.getUser().getId());
            //操作clob--写
            String s=a.getContent();
            StringReader sr=new StringReader(s);

            pstmt.setCharacterStream(3,sr,s.length());

            flag=pstmt.executeUpdate()>0?true:false;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;

    }

    @Override
    public boolean delZArticle(int id) {
        PreparedStatement pstmt=null;
        boolean flag=false;
        try {
            String sql="delete from article where id=? or rootid=?";//删除主贴，从贴也必须删除
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.setInt(2,id);
            flag=pstmt.executeUpdate()>0?true:false;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }
    @Override
    public boolean delCArticle(int id) {
        PreparedStatement pstmt=null;
        boolean flag=false;
        try {
            String sql="delete from article where id=? ";//
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,id);

            flag=pstmt.executeUpdate()>0?true:false;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }
    @Override
    public ReArticle queryReplay(int id) {
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        ReArticle re=new ReArticle();
        try {
            String sql="select p.title,t.* \n" +
                    " from \n" +
                    "\t(select a.id,a.title,a.content,a.userid,a.rootid from article a \n" +
                    "\tinner join (bbsuser u) on(a.userid=u.id) \n" +
                    "\twhere  a.rootid=? ) t,article p \n" +
                    " where p.id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.setInt(2,id);


            rs=pstmt.executeQuery();
            List<Article> list1=new ArrayList<>();
            while(rs.next()){
                re.setTitle(rs.getString("p.title"));//主贴title

                Article a=new Article();
                a.setId(rs.getInt("t.id"));
                a.setContent(rs.getString("t.content"));
                a.setTitle(rs.getString("t.title"));
                a.setRootid(rs.getInt("t.rootid"));

                BBSUser user=new BBSUser();
                user.setId(rs.getInt("t.userid"));


                a.setUser(user);
                list1.add(a);


            }

            re.setList(list1);

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
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return re;
    }
}
