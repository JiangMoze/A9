package com.weikun.dao;

import com.weikun.vo.BBSUser;

/**
 * Created by Administrator on 2016/10/9.
 */
public interface IUserDAO {
    public BBSUser login(BBSUser user);
    public boolean addUser(BBSUser user);
    public byte[] queryPicByid(int  id);

}
