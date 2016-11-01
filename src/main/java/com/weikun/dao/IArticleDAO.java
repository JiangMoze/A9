package com.weikun.dao;

import com.weikun.vo.Article;
import com.weikun.vo.PageBean;
import com.weikun.vo.ReArticle;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface IArticleDAO {
    public PageBean queryAll(int curPage,int usrid);
    public boolean addArticle(Article a);
    public boolean delZArticle(int id);//id 主帖子的主键
    public boolean delCArticle(int id);//ids 删除从贴
       /**
     *
     * @param id:主贴的id
     * @return：所有该主贴的回帖
     */
    public ReArticle queryReplay(int id);

}
