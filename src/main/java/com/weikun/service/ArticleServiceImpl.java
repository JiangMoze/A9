package com.weikun.service;

import com.weikun.dao.ArticleDAOImpl;
import com.weikun.dao.IArticleDAO;
import com.weikun.vo.PageBean;

/**
 * Created by Administrator on 2016/10/12.
 */
public class ArticleServiceImpl implements  IArticleService {
    private IArticleDAO dao=new ArticleDAOImpl();
    @Override
    public PageBean queryAll(int curPage, int usrid) {
        return dao.queryAll(curPage,usrid);
    }
}
