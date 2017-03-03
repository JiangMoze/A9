package com.moze.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description: User对象 携带用户对象的信息
 * User: Moze
 * Date: 2017-01-09
 * Time: 20:02
 */
public class BBSUser implements Serializable{
    private int id;
    private String path;//头像地址
    private String username;
    private String password;
    private int pagenum;//分页数

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }
    public BBSUser() {}
    public BBSUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
