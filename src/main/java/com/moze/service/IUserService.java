package com.moze.service;

import com.moze.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;

/**
 * Created with IntelliJ IDEA.
 * Description: Service层 User服务接口
 * User: Moze
 * Date: 2017-01-12
 * Time: 11:23
 */
public interface IUserService {
    public BBSUser login(BBSUser user);//登陆

    public BBSUser uploadPic(String tpath, FileItemIterator it);//上传头像

    public boolean addUser(BBSUser user);//注册

    public byte[] queryPicByid(int id);//读取头像
}
