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
 * Created by Administrator on 2016/10/9.
 */
public class UserServiceImpl implements IUserService {
    private IUserDAO dao=new UserDAOImpl();
    public BBSUser login(BBSUser user) {
        return dao.login(user);
    }

    private String savePath;//真正的头像存储目录
    private Map<String,String> types=new HashMap<String,String>();//存储能上传的头像类型
    public UserServiceImpl(){
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");



    }

    /**
     *
     * @param tpath:tomcat路径
     */
    public BBSUser uploadPic(String tpath,FileItemIterator it) {
        //设置上传文件的各个参数
        BBSUser user=new BBSUser();
        try {

            while(it.hasNext()){//遍历request中的所有字段，包括文件域，和字段域
                FileItemStream fis=it.next();

                String name=fis.getFieldName();//文件域的名字

                InputStream is=fis.openStream();
                if((!fis.isFormField())&&(fis.getName().length()>0)){//文件域,并且已经选择了头像

                    //得到上传文件类型
                    String type=fis.getContentType();
                    //得到上传文件的那个流

                    savePath = "" + System.getProperty("file.separator") + "upload";
                    if(!types.containsKey(type)){
                        break;
                    }
                    //创建的是全局唯一文件名字
                    UUID uu=UUID.randomUUID();
                    String filename=uu.toString()+types.get(type);

                    File saveDir=new File(tpath+savePath+"/"+filename);//真是的含tomcat的上传头像路径

                    //上传拷贝
                    try( BufferedInputStream bis=new BufferedInputStream(is);
                         FileOutputStream fos=new FileOutputStream(saveDir);
                         BufferedOutputStream bos=new BufferedOutputStream(fos);){

                        Streams.copy(bis,bos,true);//拷贝完毕
                        //2.考进数据库
                        user.setPath(tpath+savePath+"/"+filename);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{//字段域，取出用户名 密码
                    switch (name){
                        case "reusername":
                            user.setUsername(Streams.asString(is,"utf-8"));
                            break;
                        case "repassword":
                            user.setPassword(Streams.asString(is,"utf-8"));
                            break;


                    }

                }
            }
            user.setPagenum(5);

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;

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
