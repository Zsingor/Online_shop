package com.shop.controller;

import com.shop.entity.Manager;
import com.shop.entity.Result;
import com.shop.entity.User;
import com.shop.service.ManagerService;
import com.shop.utility.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PostMapping("/managerlogin")
    public Result login(@RequestBody Manager manager)
    {
        Manager data=managerService.login(manager);
        //登录成功，生成令牌并下发
        if(data!=null)
        {
            Map<String, Object> claims=new HashMap<>();
            claims.put("managername",data.getManagername());
            String token= JwtUtils.generateJWT(claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }

    //管理员注册
    @PostMapping("/managerregister")
    public Result register(@RequestBody Manager manager)
    {
        String flag=managerService.register(manager);
        if(flag.equals("0"))
        {
            return Result.success();
        }
        else
        {
            return Result.error("该用户名已存在");
        }
    }

    //管理员注销
    @PostMapping("/managerdelete")
    public Result delete(@RequestBody Manager manager)
    {
        int flag=managerService.delete(manager);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("注销失败");
        }
    }
}
