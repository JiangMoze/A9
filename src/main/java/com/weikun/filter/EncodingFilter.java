package com.weikun.filter;
import  javax.servlet.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 过滤请求的编码 设置为UTF-8
 * User: Moze
 * Date: 2017-01-09
 * Time: 19:57
 */
@WebFilter(filterName = "EncodingFilter",urlPatterns ="*" ,
        initParams =@WebInitParam(name="encoder",value  ="utf-8")) //设置默认参数
public class EncodingFilter implements javax.servlet.Filter {
    private String encoder="";
    public void destroy() {
    }

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, java.io.IOException {
        //设置请求编码为设定值  然后进行转发
        req.setCharacterEncoding(encoder);
        chain.doFilter(req, resp);
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {
        encoder=config.getInitParameter("encoder");
    }

}
