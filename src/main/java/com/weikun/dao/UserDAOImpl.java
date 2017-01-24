package com.weikun.dao;

import com.weikun.db.DruidDB;
import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.util.Streams;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description: DAO层User类的实现
 * User: Moze
 * Date: 2017-01-10
 * Time: 8:53
 */
public class UserDAOImpl implements IUserDAO {
    private Connection conn = null;
    private String savePath;//真正的头像存储目录
    private Map<String, String> types = new HashMap<String, String>();//存储能上传的头像类型

    //默认图片格式设置
    public UserDAOImpl() {
        conn = DruidDB.getConnection();
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");
    }

    /**
     * 登陆
     *
     * @param user:传递要登陆用户名和密码
     * @return 登陆成功  返回登陆的用户对象
     * 登陆失败  返回空的用户对象
     */
    public BBSUser login(BBSUser user) {
        BBSUser user1 = null;//要返回的对象
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            /**
             * 字段说明
             * id 用户id
             * username 用户名
             * password 用户密码
             */
            pstmt = conn.prepareStatement("select * from bbsuser where username=? and password=?  ");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            rs = pstmt.executeQuery();
            if (rs.next()) {//若结果存在 则登陆成功 存入user1  否则user1为空
                user1 = new BBSUser();
                user1.setId(rs.getInt("id"));
                user1.setUsername(rs.getString("username"));
                user1.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return user1;//返回user1对象
        }
    }

    /**
     * 注册
     *
     * @param user 要注册的用户对象
     * @return 注册结果
     * true 注册成功
     * false 注册失败
     */
    @Override
    public boolean addUser(BBSUser user) {
        PreparedStatement pstmt = null;
        boolean flag = false;//标志注册是否成功
        try {
            /**
             * 字段说明
             * username 用户名
             * password 密码
             * pic 头像blob数据
             * pagenum 分页数
             */
            pstmt = conn.prepareStatement("insert into bbsuser (username,password,pic,pagenum) values(?,?,?,?); ");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            FileInputStream is = new FileInputStream(user.getPath());
            //blob
            pstmt.setBinaryStream(3, is, is.available());
            pstmt.setInt(4, user.getPagenum());
            flag = pstmt.executeUpdate() > 0 ? true : false;//执行成功则返回真
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return flag;
        }
    }

    /**
     * 读取头像
     *
     * @param id 读取用户的id
     * @return 头像图片的字节码
     */
    @Override
    public byte[] queryPicByid(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        byte[] buffer = null;
        try {
            /**
             * 字段说明
             * id 用户id
             */
            pstmt = conn.prepareStatement("select * from bbsuser where id=?  ");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                //读取blob
                Blob b = rs.getBlob("pic");//获取blob格式数据  并转化为字节
                buffer = b.getBytes(1, (int) (b.length()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return buffer;//返回头像的字节格式
    }

    /**
     * 上传头像
     *
     * @param tpath 服务器路径
     * @param it    上传信息
     * @return 用户对象
     */
    @Override
    public BBSUser uploadPic(String tpath, FileItemIterator it) {
        //设置上传文件的各个参数
        BBSUser user = new BBSUser();
        try {
            while (it.hasNext()) {//遍历request中的所有字段，包括文件域，和字段域
                FileItemStream fis = it.next();
                String name = fis.getFieldName();//字段的键名
                InputStream is = fis.openStream();
                //对请求字段按文件域和字段域分别处理
                if ((!fis.isFormField()) && (fis.getName().length() > 0)) {//文件域,并且已经选择了头像
                    String type = fis.getContentType();//得到上传文件类型
                    savePath = "" + System.getProperty("file.separator") + "upload";
                    if (!types.containsKey(type)) {
                        break;
                    }
                    UUID uu = UUID.randomUUID();  //创建的是全局唯一文件名字
                    String filename = uu.toString() + types.get(type);//通过文件类型找到文件后缀名
                    File saveDir = new File(tpath + savePath + "/" + filename);//头像上传的物理路径
                    //上传拷贝
                    try (BufferedInputStream bis = new BufferedInputStream(is);
                         FileOutputStream fos = new FileOutputStream(saveDir);
                         BufferedOutputStream bos = new BufferedOutputStream(fos);) {
                        Streams.copy(bis, bos, true);//拷贝完毕
                        //2.考进数据库
                        user.setPath(tpath + savePath + "/" + filename);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {    //字段域，取出用户名 密码
                    switch (name) {
                        case "reusername":
                            user.setUsername(Streams.asString(is, "utf-8"));
                            break;
                        case "repassword":
                            user.setPassword(Streams.asString(is, "utf-8"));
                            break;
                    }
                }
            }
            user.setPagenum(5);//设置用户的默认分页为5
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;//返回注册的用户对象
    }
}
