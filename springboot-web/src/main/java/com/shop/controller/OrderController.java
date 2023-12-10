package com.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shop.entity.Order;
import com.shop.entity.Result;
import com.shop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class OrderController
{
    @Autowired
    private OrderService orderService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @PostMapping("/sendemail")
    public void sendTextMail(@RequestBody List<Order> orderlist)
    {
        String username=orderlist.get(0).getUsername();
        String to= userService.selectemail(username);
        String subject="订单信息";
        String text="";
        String goodslist="";
        int num=0;
        Date time=new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String buytime = df.format(time);
        for (Order order : orderlist)
        {
            goodslist+= order.getGoodsname()+" "+order.getNumber()+" 件, ";
            num++;
        }
        text=String.format("您在 "+buytime+" 总共购买了 %s 种商品，分别为 "+goodslist, num);
        mailService.sendTextMailMessage(to,subject,text);
    }

    @PostMapping("/billing")
    public Result billing(@RequestBody List<Order> orderlist)
    {
        int flag=0;
        int fla=0;
        for (Order order : orderlist)
        {
            flag = orderService.addorder(order);
            fla=saleService.addsale(order);
            if (flag != 1 || fla!=1) {
                break;
            }
        }
        if(flag==1 && fla==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("购买失败");
        }
    }

    @PostMapping("/adduserorder")
    public Result adduserorder(@RequestBody Order order)
    {
        int flag=orderService.addorder(order);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("添加失败");
        }
    }

    @PostMapping("/deleteorder")
    public Result deleteorder(@RequestBody Order order)
    {
        int flag=orderService.deleteorder(order);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("删除失败");
        }
    }

    @PostMapping("/selectorder")
    public Result selectorder()
    {
        List<Order> data=orderService.selectorder();
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("查询失败");
        }
    }

    @PostMapping("/selectuserorder")
    public Result selectuserorder(@RequestBody Order order)
    {
        List<Order> data=orderService.selectuserorder(order);
        System.out.println(123);
        System.out.println(data);
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("查询失败");
        }
    }
}
