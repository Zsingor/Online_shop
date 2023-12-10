package com.shop.controller;


import cn.hutool.core.io.FileUtil;
import com.shop.entity.Goods;
import com.shop.entity.Result;
import com.shop.service.GoodsService;
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

@Slf4j
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/goodsshow")
    public Result showgoods()
    {
        List<Goods> data=goodsService.showgoods();
        return Result.success(data);
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

    @PostMapping("/goodsadd")
    public Result addgoods(@RequestBody Goods goods)
    {
        System.out.println(goods);
        int flag=goodsService.addgoods(goods);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("添加失败");
        }
    }

    @PostMapping("/goodsdelete")
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
}
