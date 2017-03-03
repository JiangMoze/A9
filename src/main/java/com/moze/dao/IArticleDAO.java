package com.moze.dao;

import com.moze.vo.Article;
import com.moze.vo.PageBean;
import com.moze.vo.ReArticle;

/**
 * Created with IntelliJ IDEA.
 * Description: DAO层Article接口  提供发帖回帖和删除功能
 * User: Moze
 * Date: 2017-01-09
 * Time: 20:08
 */
public interface IArticleDAO {
    public PageBean queryAll(int curPage, int usrid);//查询主贴

    public boolean addArticle(Article a);//发帖

    public boolean delZArticle(int id);//删除主贴

    public boolean delCArticle(int id);//删除从贴

    public ReArticle queryReplay(int id);//查询从贴

}
