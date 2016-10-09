package com.weikun.service;

import com.sun.org.apache.bcel.internal.generic.IUSHR;
import com.weikun.dao.IUserDAO;
import com.weikun.dao.UserDAOImpl;
import com.weikun.vo.BBSUser;

/**
 * Created by Administrator on 2016/10/9.
 */
public class UserServiceImpl implements IUserService {
    private IUserDAO dao=new UserDAOImpl();
    public boolean login(BBSUser user) {
        return dao.login(user);
    }
}
