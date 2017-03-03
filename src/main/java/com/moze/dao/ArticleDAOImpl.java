package com.moze.dao;

import com.moze.db.DBCPDB;
import com.moze.vo.Article;
import com.moze.vo.BBSUser;
import com.moze.vo.PageBean;
import com.moze.vo.ReArticle;

import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: DAO层Article类的实现
 * User: Moze
 * Date: 2017-01-09
 * Time: 20:13
 */
public class ArticleDAOImpl implements IArticleDAO {
    private Connection conn;

    public ArticleDAOImpl() {//默认构造器获得conn对象
        conn = DBCPDB.getDBCPConnection();
    }

    /**
     * 查询所有主贴
     * @param curPage 当前页数据
     * @param usrid 用户id
     * @return 分页对象
     */
    @Override
    public PageBean queryAll(int curPage, int usrid) {
        CallableStatement cs = null;//调用过程函数  使用CallableStatement
        ResultSet rs = null;
        ArrayList<Article> list = new ArrayList<Article>();
        PageBean pb = new PageBean();
        try {
            /**
             * 输入参数 字段
             * curpage 当前页
             * usrid   用户id
             * 返回参数 字段
             * maxpage 最大页数
             * maxrowcount 最大行数
             * rowsperpage 每页行数
             */
            String sql = " call q4(?,?,?,?,?) ";
            cs = conn.prepareCall(sql);
            cs.setInt(1, curPage);//输入参数
            cs.setInt(2, usrid);
            cs.registerOutParameter(3, Types.INTEGER);//输出参数
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.INTEGER);
            boolean flag = cs.execute();//执行结果标志
            while (flag) {
                rs = cs.getResultSet();
                pb.setCurPage(curPage);//当前页
                pb.setMaxPage(cs.getInt(3));
                pb.setMaxRowCount(cs.getInt(4));
                pb.setRowsPerPage(cs.getInt(5));
                while (rs.next()) {
                    Article a = new Article();
                    BBSUser user = new BBSUser();
                    a.setId(rs.getInt("id"));
                    a.setTitle(rs.getString("title"));
                    a.setContent(rs.getString("content"));
                    a.setDatetime(rs.getString("datetime"));
                    user.setId(rs.getInt("userid"));
                    a.setUser(user);
                    list.add(a);
                }
                pb.setData(list);//每页的数据
                flag = cs.getMoreResults();//是否有更多的结果集
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pb;
    }

    /**
     * 发帖
     * @param a 要发布的帖子对象
     * @return 发布结果
     *          true 发布成功
     *          false 发布失败
     */
    @Override
    public boolean addArticle(Article a) {
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            /**
             * 字段说明
             * rootid 主贴id
             * title 主贴标题
             * content 主贴内容
             * datetime 发布日期
             * userid 用户id
             */
            String sql = "INSERT into article(rootid,title,content,datetime,userid) values(?,?,?,now(),?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, a.getRootid());
            pstmt.setString(2, a.getTitle());
            pstmt.setInt(4, a.getUser().getId());
            //操作clob--写
            String s = a.getContent();
            StringReader sr = new StringReader(s);
            pstmt.setCharacterStream(3, sr, s.length());
            flag = pstmt.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 删除主贴
     * @param id 删除帖子id
     * @return 删除结果
     *          true 删除成功
     *          false 删除失败
     */
    @Override
    public boolean delZArticle(int id) {
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            /**
             * 字段说明
             * id 删除帖子id
             * rootid 删除主贴 则其从贴也一起删除
             */
            String sql = "delete from article where id=? or rootid=?";//删除主贴，从贴也必须删除
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, id);
            flag = pstmt.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 删除从贴
     * @param id 删除从贴id
     * @return 删除结果
     *          true 删除成功
     *          false 删除失败
     */
    @Override
    public boolean delCArticle(int id) {
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            String sql = "delete from article where id=? ";//
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            flag = pstmt.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 查询回帖
     * @param id 查询的帖子
     * @return  回帖对象
     */
    @Override
    public ReArticle queryReplay(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReArticle re = new ReArticle();
        try {
            String sql = "select p.title,t.* \n" +
                    " from \n" +
                    "\t(select a.id,a.title,a.content,a.userid,a.rootid from article a \n" +
                    "\tinner join (bbsuser u) on(a.userid=u.id) \n" +
                    "\twhere  a.rootid=? ) t,article p \n" +
                    " where p.id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, id);
            rs = pstmt.executeQuery();
            List<Article> list1 = new ArrayList<>();
            while (rs.next()) {
                re.setTitle(rs.getString("p.title"));//主贴title
                Article a = new Article();
                a.setId(rs.getInt("t.id"));
                a.setContent(rs.getString("t.content"));
                a.setTitle(rs.getString("t.title"));
                a.setRootid(rs.getInt("t.rootid"));
                BBSUser user = new BBSUser();
                user.setId(rs.getInt("t.userid"));
                a.setUser(user);
                list1.add(a);
            }
            re.setList(list1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return re;
    }
}
