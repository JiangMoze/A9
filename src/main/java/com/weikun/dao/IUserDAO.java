package com.weikun.dao;

import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;

/**
 * Created with IntelliJ IDEA.
 * Description: DAO层User接口 提供用户的登陆注册和头像读取功能
 * User: Moze
 * Date: 2017-01-09
 * Time: 20:11
 */
public interface IUserDAO {
    public BBSUser login(BBSUser user);//登陆 用户查询

    public boolean addUser(BBSUser user);//注册 用户添加

    public byte[] queryPicByid(int id);//查询用户头像

    public BBSUser uploadPic(String tpath, FileItemIterator it);//上传头像
}
