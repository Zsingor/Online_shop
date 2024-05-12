package com.shop.controller;


import cn.hutool.core.io.FileUtil;
import com.shop.entity.*;
import com.shop.service.GoodsService;
import com.shop.service.SalesService;
import com.shop.utility.AutoLog;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SalesService salesService;

    @PostMapping("/goodsshow")
    public Result showgoods()
    {
        try {
            List<Goods> data=goodsService.showgoods();
            return Result.success(data);
        }
        catch (Exception error){
            return Result.error("获取数据失败");
        }
    }

    @PostMapping("/goodsget")
    @AutoLog(operate = "查看商品信息",identify = "销售员")
    public Result getgoods(@RequestBody Sales sales)
    {
        try {
            List<Goods> data=goodsService.getgoods(sales);
            return Result.success(data);
        }
        catch (Exception error){
            return Result.error("获取数据失败");
        }
    }

    @PostMapping("/goodsselect")
    public Result selectgoods(@RequestBody Goods goods)
    {
        Goods data=goodsService.selectgoods(goods);
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("查询失败");
        }
    }

    @PostMapping("/goodschange")
    @AutoLog(operate = "修改商品信息",identify = "管理员")
    public Result updategoods(@RequestBody Goods goods)
    {
        int flag=goodsService.update(goods);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("修改失败");
        }
    }

    @PostMapping("/salesgoodschange")
    @AutoLog(operate = "修改商品信息",identify = "销售员")
    public Result updatesalesgoods(@RequestBody Goods goods)
    {
        int flag=goodsService.update(goods);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("修改失败");
        }
    }

    @PostMapping("/goodsadd")
    @AutoLog(operate = "添加商品",identify = "管理员")
    public Result addgoods(@RequestBody Goods goods)
    {
        Sales sales=new Sales();
        sales.setId(goods.getSalesid());
        Sales temp=salesService.select(sales);
        if(temp==null)
        {
            return Result.error("该销售员ID不存在");
        }
        int flag=goodsService.addgoods(goods);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("该商品编号已存在");
        }
    }

    @PostMapping("/salesgoodsadd")
    @AutoLog(operate = "添加商品",identify = "销售员")
    public Result salesaddgoods(@RequestBody Goods goods)
    {
        Sales sales=new Sales();
        sales.setId(goods.getSalesid());
        Sales temp=salesService.select(sales);
        if(temp==null)
        {
            return Result.error("该销售员ID不存在");
        }
        int flag=goodsService.addgoods(goods);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("该商品编号已存在");
        }
    }

    @PostMapping("/goodsdelete")
    @AutoLog(operate = "删除商品",identify = "管理员")
    public Result deletegoods(@RequestBody Goods goods)
    {
        int flag=goodsService.deletegoods(goods);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("删除失败");
        }
    }

    @PostMapping("/salesgoodsdelete")
    @AutoLog(operate = "删除商品",identify = "销售员")
    public Result salesdeletegoods(@RequestBody Goods goods)
    {
        int flag=goodsService.deletegoods(goods);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("删除失败");
        }
    }

    //用于查询对应销售员的商品销售情况
    @PostMapping("/goodssalesearch")
    @AutoLog(operate = "查询销售员的商品销售情况",identify = "管理员")
    public Result goodssale(@RequestBody Sales sales)
    {
        List<Goods> data=goodsService.goodssale(sales);
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("查询失败");
        }
    }

    //管理员获得对应的商品销售信息
    @PostMapping("/goodssaleall")
    @AutoLog(operate = "查询商品销售信息",identify = "管理员")
    public Result goodsallsale()
    {
        List<Goods> data=goodsService.goodsallsale();
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("查询失败");
        }
    }

    //销售员查询商品的各类型
    @PostMapping("/goodssaletype")
    public Result selecttype(@RequestBody Sales sales)
    {
        List<GoodsType> data=goodsService.selecttype(sales);
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("无销售情况");
        }
    }

    //管理员查询商品的各类型
    @PostMapping("/goodsalltype")
    public Result selectalltype()
    {
        List<GoodsType> data=goodsService.selectalltype();
        if(data!=null)
        {
            return Result.success(data);
        }
        else
        {
            return Result.error("无销售情况");
        }
    }

    //获取推荐商品的列表
    @PostMapping("/showrecommend")
    public Result showrecommend(@RequestBody User user)
    {
        try {
            List<Goods> data=goodsService.getrecommendGoods(user);
            return Result.success(data);
        }
        catch (Exception error){
            return Result.error("获取数据失败");
        }
    }
}
