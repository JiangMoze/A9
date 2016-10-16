package com.weikun.service;

import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface IArticleService {
    public PageBean queryAll(int curPage, int usrid) ;
    public boolean addArticle(Article a) ;
}
