package com.weikun.dao;

import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface IArticleDAO {
    public PageBean queryAll(int curPage,int usrid);
    public boolean addArticle(Article a);

}
