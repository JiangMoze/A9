package com.moze.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description: Article对象 存储主贴或从贴 并携带发布人信息
 * User: Moze
 * Date: 2017-01-09
 * Time: 20:01
 */
public class Article implements Serializable {
    private int id;
    private int rootid;
    private String title;
    private String content;
    private String datetime;
    private BBSUser user;

    public BBSUser getUser() {
        return user;
    }

    public void setUser(BBSUser user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRootid() {
        return rootid;
    }

    public void setRootid(int rootid) {
        this.rootid = rootid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
