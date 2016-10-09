package com.weikun.service;

import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;

/**
 * Created by Administrator on 2016/10/9.
 */
public interface IUserService {
    public BBSUser login(BBSUser user) ;
    public BBSUser uploadPic(String tpath,FileItemIterator it);//上传头像
    public boolean addUser(BBSUser user) ;
    public byte[] queryPicByid(int id) ;
}
