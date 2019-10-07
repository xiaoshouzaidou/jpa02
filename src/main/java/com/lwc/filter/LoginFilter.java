package com.lwc.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2019/9/5.
 */
@WebFilter(urlPatterns = "/*",filterName = "LoginFilter")
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("进入过滤器！");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        //0. 转换
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1. 获取请求资源，截取
        String uri = request.getRequestURI();   // /emp_sys/login.jsp
        // 截取 【login.jsp或login】
        String requestPath = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
        System.out.println(requestPath);

        //2. 判断： 先放行一些资源：/login.jsp、/login
        if ("login".equals(requestPath) || "loginInfo".equals(requestPath)) {
            // 放行
            chain.doFilter(request, response);
        }
        else {
            //3. 对其他资源进行拦截
            //3.1 先获取Session、获取session中的登陆用户(loginInfo)
            HttpSession session = request.getSession(false);
            System.out.println("拿到session");
            // 判断
            if (session != null) {

                System.out.println("session不为空");
                Object obj = session.getAttribute("user");

                //3.2如果获取的内容不为空，说明已经登陆，放行
                if (obj != null) {
                    // 放行
                     if("list".equals(requestPath)){
                         System.out.println("放行！");
                         chain.doFilter(request, response);
                     }else{
                         chain.doFilter(request, response);
                     }
                } else {
                    //3.3如果获取的内容为空，说明没有登陆； 跳转到登陆
                    uri = "/login";
                }

            } else {
                // 肯定没有登陆
                System.out.println("session为空");
                uri = "/login";
            }
            request.getRequestDispatcher(uri).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
