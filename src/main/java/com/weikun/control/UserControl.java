package com.weikun.control;

import com.weikun.service.IUserService;
import com.weikun.service.UserServiceImpl;
import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/9.
 */
@WebServlet(name = "UserControl",urlPatterns ={"/user"},
        initParams = {
                @WebInitParam(name = "success",value = "/article?curpage=1")
})
public class UserControl extends HttpServlet {
    private IUserService service=new UserServiceImpl();
    private Map<String,String> map=new HashMap<String,String>();
    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("success",config.getInitParameter("success"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(ServletFileUpload.isMultipartContent(request)){//为真代表是流的形式上传 form表单的属性enctype="multipart/form-data"
            //注册用的
            String tpath=request.getServletContext().getRealPath("/");//tomcat路径
            String tmpPath = "" + System.getProperty("file.separator") + "tmpdir";
            File tmpDir= new File(tmpPath);;//头像临时存储目录

            if(!tmpDir.isDirectory()){
                tmpDir.mkdir();
            }

            DiskFileItemFactory dif=new DiskFileItemFactory();
            dif.setSizeThreshold(1024*1024*10);//缓冲区大小 10M
            dif.setRepository(tmpDir);//设置临时目录

            ServletFileUpload sf=new ServletFileUpload(dif);
            sf.setFileSizeMax(1024*1024*5);//单个文件大小
            sf.setSizeMax(1024*1024*50);//总共文件大小
            try {
                FileItemIterator it=sf.getItemIterator(request);



                BBSUser user=service.uploadPic(tpath,it);

                if(service.addUser(user)){
                    RequestDispatcher dispatcher=null;
                    String success=map.get("success");
                    dispatcher=request.getRequestDispatcher(success);
                    dispatcher.forward(request,response);
                }

            } catch (FileUploadException e) {
                e.printStackTrace();
            }

        }else{//为假代表是键值对的形式上传 form表单的属性enctype="application/x-www-form-urlencoded"

            String action=request.getParameter("action");
            switch (action){
                case "login":
                    login(request, response);
                    break;
                case "read":
                    read(request, response);
                    break;

            }


        }
    }
//读取头像
    private void read(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        byte[] buffer=service.queryPicByid(Integer.parseInt(id));
        try {
            OutputStream os=response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String pwd=request.getParameter("password");
        BBSUser user=new BBSUser(username,pwd);
        RequestDispatcher dispatcher=null;
        String success=map.get("success");
        BBSUser user1=service.login(user);
        if(  (user1!=null)){//登录成功。1.跳转到主页并传递登录成功消息 2.操作cookie 3.放到session里面

            request.setAttribute("info","登录成功！");
            request.getSession().setAttribute("user",user1);
            //cookie

            Cookie c=new Cookie("http://www.papaok.org/username",username);
            c.setMaxAge(3600*24*7);
            response.addCookie(c);

            Cookie c1=new Cookie("http://www.papaok.org/pwd",pwd);
            c1.setMaxAge(3600*24*7);
            response.addCookie(c1);

        }else{//登录失败 1.跳转到主页并传递登录失败消息
            request.setAttribute("info","登录失败，请重新登录！");

        }
        dispatcher=request.getRequestDispatcher(success);
        dispatcher.forward(request,response);
    }
}
