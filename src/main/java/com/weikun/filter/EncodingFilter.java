package com.weikun.filter;
import  javax.servlet.annotation.*;
/**
 * Created by Administrator on 2016/9/30.
 */
@WebFilter(filterName = "EncodingFilter",urlPatterns ="*" ,
        initParams =@WebInitParam(name="encoder",value  ="utf-8"))
public class EncodingFilter implements javax.servlet.Filter {
    private String encoder="";
    public void destroy() {
    }

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, java.io.IOException {

        req.setCharacterEncoding(encoder);



        chain.doFilter(req, resp);

    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {
        encoder=config.getInitParameter("encoder");
    }

}
