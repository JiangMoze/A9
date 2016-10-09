package com.weikun.control;

import com.weikun.service.IUserService;
import com.weikun.service.UserServiceImpl;
import com.weikun.vo.BBSUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/9.
 */
@WebServlet(name = "UserControl",urlPatterns ={"/user"},
        initParams = {
                @WebInitParam(name = "success",value = "/show.jsp")




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
        String username=request.getParameter("username");
        String pwd=request.getParameter("password");
        BBSUser user=new BBSUser(username,pwd);
        RequestDispatcher dispatcher=null;
        String success=map.get("success");
        if(service.login(user)){//登录成功。1.跳转到主页并传递登录成功消息 2.操作cookie 3.放到session里面

            request.setAttribute("info","登录成功！");
            request.getSession().setAttribute("user",user);
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
