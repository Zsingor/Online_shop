package com.shop.controller;

import cn.hutool.core.io.FileUtil;
import com.shop.entity.Result;
import com.shop.entity.User;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import com.shop.utility.JwtUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@CrossOrigin
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    //用户登录
    @PostMapping( "/login")
    public Result login(@RequestBody User user)
    {
        User data=userService.login(user);
        //登录成功，生成令牌并下发
        if(data!=null)
        {
            Map<String, Object> claims=new HashMap<>();
            claims.put("username",data.getUsername());
            String token=JwtUtils.generateJWT(claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }

    //用户注册
    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
        String flag=userService.register(user);
        if(flag.equals("0"))
        {
            return Result.success();
        }
        else
        {
            return Result.error("该用户名已存在");
        }
    }

    //用户查询
    @PostMapping( "/select")
    public Result select(@RequestBody User user)
    {
        User data=userService.select(user);
        return Result.success(data);
    }

    //用户注销
    @PostMapping("/delete")
    public Result delete(@RequestBody User user)
    {
        int flag=userService.delete(user);
        int fla=orderService.deleteuserorder(user.getUsername());
        if(flag==1&&fla==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("注销失败");
        }
    }

    //用户修改
    @PostMapping("/update")
    public Result update(@RequestBody User user)
    {
        int flag=userService.updatecount(user);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("修改失败");
        }
    }

    //用户密码验证
    @PostMapping("/checkpwd")
    public Result checkpwd(@RequestBody User user)
    {
        int flag=userService.checkpwd(user);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("当前密码错误");
        }
    }

    //用户密码修改
    @PostMapping("/updatepwd")
    public Result updatepwd(@RequestBody User user)
    {
        int flag=userService.updatepwd(user);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("密码更新失败");
        }
    }

    //进行登录校验
    @PostMapping("/checklogin")
    public Result checklogin()
    {
        return Result.success();
    }

}
