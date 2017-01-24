package com.weikun.service;

import com.sun.org.apache.bcel.internal.generic.IUSHR;
import com.weikun.dao.IUserDAO;
import com.weikun.dao.UserDAOImpl;
import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
