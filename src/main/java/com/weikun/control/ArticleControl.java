package com.weikun.control;

import com.weikun.service.ArticleServiceImpl;
import com.weikun.service.IArticleService;
import com.weikun.vo.Article;
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
        String action=request.getParameter("action");//功能分开标签

        switch(action){
            case "page":
                page(request, response);
                break;

            case "addz"://主贴

                addz(request, response);
                break;
            case "delz"://删除主贴
                del(request, response);
                break;
        }
    }

    private void del(HttpServletRequest request, HttpServletResponse response) {

        String id=request.getParameter("id");//帖子主键

        if(service.delArticle(Integer.parseInt(id))){//删除成功
            RequestDispatcher dispatcher=request.getRequestDispatcher("article?action=page&curpage=1");
            try {
                dispatcher.forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void addz(HttpServletRequest request, HttpServletResponse response) {
        String rootid=request.getParameter("rootid");
        Article a=new Article();
        a.setRootid(Integer.parseInt(rootid));
        a.setTitle(request.getParameter("title"));
        a.setContent(request.getParameter("content"));
        BBSUser user=(BBSUser)request.getSession().getAttribute("user");


        a.setUser(user);

        if(service.addArticle(a)){

            RequestDispatcher dispatcher=request.getRequestDispatcher("article?action=page&curpage=1");
            try {
                dispatcher.forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void page(HttpServletRequest request, HttpServletResponse response) {
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


        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
