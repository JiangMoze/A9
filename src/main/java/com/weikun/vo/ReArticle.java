package com.weikun.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
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
