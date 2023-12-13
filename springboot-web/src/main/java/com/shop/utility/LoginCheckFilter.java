package com.shop.utility;

import com.alibaba.fastjson.JSONObject;
import com.shop.entity.Result;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    @Override //拦截到请求调用，调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        //获取url
        String url = req.getRequestURL().toString();

        //解决跨域问题
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type,token");

        //处理预检请求
        if(req.getMethod().equals("OPTIONS"))
        {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        //如果是登录或者注册请求则放行
        if (url.equals("http://"+ip+":"+port+"/login") | url.equals("http://"+ip+":"+port+"/register")
                | url.equals("http://"+ip+":"+port+"/managerlogin")| url.equals("http://"+ip+":"+port+"/managerregister")
                | url.equals("http://"+ip+":"+port+"/goodsshow") | url.startsWith("http://"+ip+":"+port+"/download/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //获取请求头中的令牌
        String token = req.getHeader("token");
        System.out.println(token);

        //如果用户为未登录
        if (!StringUtils.hasLength(token))
        {
            System.out.println("用户未登录");
            Result error=Result.error("NO_LOGIN");
            //将对象转换为json文件并返回
            String str=JSONObject.toJSONString(error);
            res.getWriter().write(str);
            return;
        }
        else
        {
            try
            {
                JwtUtils.parseJWT(token);
                System.out.println("验证成功");
            } catch (Exception e) {
                System.out.println("验证错误");
                e.printStackTrace();
                StackTraceElement stackTraceElement= e.getStackTrace()[0];
                System.out.println("系统出错，错误信息:"+e.toString()+" at "+stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName()+":"+stackTraceElement.getLineNumber());
                Result error=Result.error("NO_LOGIN");
                //将对象转换为json文件并返回
                String str=JSONObject.toJSONString(error);
                res.getWriter().write(str);
                return;
            }
        }

        //放行
        System.out.println("已登录");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
