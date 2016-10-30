package com.weikun.service;

import com.alibaba.fastjson.JSON;
import com.weikun.dao.ArticleDAOImpl;
import com.weikun.dao.IArticleDAO;
import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/12.
 */
public class ArticleServiceImpl implements  IArticleService {
    private IArticleDAO dao=new ArticleDAOImpl();
    @Override
    public PageBean queryAll(int curPage, int usrid) {
        return dao.queryAll(curPage,usrid);
    }

    @Override
    public boolean addArticle(Article a) {
        return dao.addArticle(a);
    }

    @Override
    public boolean delArticle(int id) {
        return dao.delArticle(id);
    }

    @Override
    public String queryReplay(int id) {

        List<Article> list=dao.queryReplay(id);

        Map<String,Object> map=new HashMap<String,Object> ();

        map.put("title","主贴标题");
        map.put("list",list);



        return JSON.toJSONString(map,true);
    }
}
