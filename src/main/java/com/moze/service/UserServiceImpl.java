package com.moze.service;

import com.moze.dao.IUserDAO;
import com.moze.dao.UserDAOImpl;
import com.moze.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;

/**
 * Created with IntelliJ IDEA.
 * Description: User服务的实现 Control层调用DAO
 * User: Moze
 * Date: 2017-01-12
 * Time: 11:27
 */
public class UserServiceImpl implements IUserService {
    private IUserDAO dao = new UserDAOImpl();

    public BBSUser login(BBSUser user) {
        return dao.login(user);
    }

    @Override
    public BBSUser uploadPic(String tpath, FileItemIterator it) {
        return dao.uploadPic(tpath, it);
    }

    @Override
    public boolean addUser(BBSUser user) {
        return dao.addUser(user);
    }

    @Override
    public byte[] queryPicByid(int id) {
        return dao.queryPicByid(id);
    }
}
