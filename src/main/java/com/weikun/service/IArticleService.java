package com.weikun.service;

import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Service层 Article服务接口 链接DAO和Control层
 * User: Moze
 * Date: 2017-01-12
 * Time: 11:23
 */
public interface IArticleService {
    public PageBean queryAll(int curPage, int usrid);//查询主贴

    public boolean addArticle(Article a);//发帖

    public boolean delZArticle(int id);//删除主贴

    public boolean delCArticle(int id);//删除从贴

    public String queryReplay(int id);//查询从贴
}
