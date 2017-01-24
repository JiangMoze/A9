package com.weikun.service;

import com.alibaba.fastjson.JSON;
import com.weikun.dao.ArticleDAOImpl;
import com.weikun.dao.IArticleDAO;
import com.weikun.vo.Article;
import com.weikun.vo.PageBean;
import com.weikun.vo.ReArticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: Article服务的实现 调用DAO功能
 * User: Moze
 * Date: 2017-01-13
 * Time: 18:01
 */
public class ArticleServiceImpl implements IArticleService {
    private IArticleDAO dao = new ArticleDAOImpl();

    @Override
    public PageBean queryAll(int curPage, int usrid) {
        return dao.queryAll(curPage, usrid);
    }

    @Override
    public boolean addArticle(Article a) {
        return dao.addArticle(a);
    }

    @Override
    public boolean delZArticle(int id) {
        return dao.delZArticle(id);
    }

    @Override
    public boolean delCArticle(int id) {
        return dao.delCArticle(id);
    }

    @Override
    public String queryReplay(int id) {
        ReArticle re = dao.queryReplay(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", re.getTitle());
        map.put("list", re.getList());
        return JSON.toJSONString(map, true);
    }
}
