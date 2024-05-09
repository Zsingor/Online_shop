package com.shop.controller;

import com.shop.entity.Result;
import com.shop.entity.User;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import com.shop.utility.AutoLog;
import com.shop.utility.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    //用户登录
    @PostMapping( "/login")
    @AutoLog(operate = "登录",identify = "用户")
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
    @AutoLog(operate = "注册",identify = "用户")
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
    @AutoLog(operate = "查询个人信息",identify = "用户")
    public Result select(@RequestBody User user)
    {
        User data=userService.select(user);
        return Result.success(data);
    }

    //用户注销
    @PostMapping("/delete")
    @AutoLog(operate = "注销",identify = "用户")
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
    @AutoLog(operate = "修改账户信息",identify = "用户")
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
    @AutoLog(operate = "修改密码",identify = "用户")
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
