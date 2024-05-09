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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

        //添加放行的url请求
        Set<String> allowedUrls = new HashSet<>(Arrays.asList(
                "http://" + ip + ":" + port + "/login",
                "http://" + ip + ":" + port + "/register",
                "http://" + ip + ":" + port + "/managerlogin",
                "http://" + ip + ":" + port + "/managerregister",
                "http://" + ip + ":" + port + "/saleslogin",
                "http://" + ip + ":" + port + "/salesregister",
                "http://" + ip + ":" + port + "/goodsshow",
                "http://" + ip + ":" + port + "/showrecommend"
        ));

        //放行对应的请求
        if (url.startsWith("http://" + ip + ":" + port + "/download/") || allowedUrls.stream().anyMatch(url::equals)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
//        if (url.equals("http://"+ip+":"+port+"/login") | url.equals("http://"+ip+":"+port+"/register")
//                | url.equals("http://"+ip+":"+port+"/managerlogin")| url.equals("http://"+ip+":"+port+"/managerregister")
//                | url.equals("http://"+ip+":"+port+"/saleslogin")| url.equals("http://"+ip+":"+port+"/salesregister")
//                | url.equals("http://"+ip+":"+port+"/goodsshow") | url.equals("http://"+ip+":"+port+"/showrecommend")
//                | url.startsWith("http://"+ip+":"+port+"/download/")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }

        //获取请求头中的令牌
        String token = req.getHeader("token");

        //如果用户为未登录
        if (!StringUtils.hasLength(token))
        {
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
