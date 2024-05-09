package com.shop.controller;

import com.shop.entity.GoodsType;
import com.shop.entity.Result;
import com.shop.entity.Sales;
import com.shop.service.SalesService;
import com.shop.utility.AutoLog;
import com.shop.utility.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SalesController {
    @Autowired
    private SalesService salesService;

    //销售员登录
    @PostMapping("/saleslogin")
    @AutoLog(operate = "登录",identify = "销售员")
    public Result login(@RequestBody Sales sales)
    {
        Sales data=salesService.login(sales);
        //登录成功，生成令牌并下发
        if(data!=null)
        {
            Map<String, Object> claims=new HashMap<>();
            claims.put("username",data.getId());
            String token= JwtUtils.generateJWT(claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }

    //销售员注册
    @PostMapping("/salesregister")
    @AutoLog(operate = "注册",identify = "销售员")
    public Result register(@RequestBody Sales sales)
    {
        String flag=salesService.register(sales);
        if(flag.equals("0"))
        {
            return Result.success();
        }
        else
        {
            return Result.error("该用户名已存在");
        }
    }

    //销售员注销
    @PostMapping("/salesdelete")
    @AutoLog(operate = "注销",identify = "销售员")
    public Result delete(@RequestBody Sales sales)
    {
        int flag=salesService.delete(sales);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("删除失败");
        }
    }

    //查找所有销售员的信息
    @PostMapping("/salessshow")
    @AutoLog(operate = "查询销售员信息",identify = "管理员")
    public Result showgoods()
    {
        List<Sales> data=salesService.showsales();
        return Result.success(data);
    }

    //重置销售员的密码
    @PostMapping("/saleschange")
    @AutoLog(operate = "重置销售员密码",identify = "管理员")
    public Result updatesales(@RequestBody Sales sales)
    {
        int flag=salesService.update(sales);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("修改失败");
        }
    }

    //查询所有销售员的销售情况
    @PostMapping("/salesorder")
    @AutoLog(operate = "查询销售员业绩",identify = "管理员")
    public Result salesorder()
    {
        List<GoodsType> data=salesService.salesorder();
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("无销售情况");
        }
    }
}
