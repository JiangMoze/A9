package com.weikun.control;

import com.weikun.service.ArticleServiceImpl;
import com.weikun.service.IArticleService;
import com.weikun.vo.BBSUser;
import com.weikun.vo.PageBean;
import org.omg.CORBA.Request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/10/12.
 */
@WebServlet(name = "ArticleControl",urlPatterns = {"/article"})
public class ArticleControl extends HttpServlet {
    private IArticleService service=new ArticleServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int curPage=Integer.parseInt(request.getParameter("curpage"));
        int usid=0;
        if(request.getSession().getAttribute("user")==null){//游客
            usid=999;
        }else{
            BBSUser user=(BBSUser)request.getSession().getAttribute("user");
            usid=user.getId();
        }
        PageBean pb=service.queryAll(curPage,usid);
        request.setAttribute("pb",pb);
        RequestDispatcher dispatcher=request.getRequestDispatcher("show.jsp");


        dispatcher.forward(request,response);

    }
}
