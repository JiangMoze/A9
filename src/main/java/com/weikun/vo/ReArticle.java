package com.weikun.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: ReArticle对象
 * User: Moze
 * Date: 2017-01-09
 * Time: 20:05
 */
public class ReArticle implements Serializable{
    private String title;//主贴title
    private List<Article> list; //所有回帖

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Article> getList() {
        return list;
    }

    public void setList(List<Article> list) {
        this.list = list;
    }
}
