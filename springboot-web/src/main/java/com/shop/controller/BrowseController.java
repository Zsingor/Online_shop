package com.shop.controller;

import com.shop.entity.BrowseTime;
import com.shop.entity.Goods;
import com.shop.entity.Result;
import com.shop.service.BrowseService;
import com.shop.utility.AutoLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class BrowseController {
    @Autowired
    private BrowseService browseService;

    @PostMapping("/getbrowse")
    @AutoLog(operate = "查看浏览日志",identify = "管理员")
    public Result getbrowse()
    {
        try {
            List<BrowseTime> data=browseService.getbrowse();
            return Result.success(data);
        }
        catch (Exception error){
            return Result.error("获取数据失败");
        }
    }

    @PostMapping("/addbrowse")
    public Result addbrowse(@RequestBody BrowseTime browseTime)
    {
        int flag=browseService.addbrowse(browseTime);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("浏览时间信息添加失败");
        }
    }

    @PostMapping("/deletebrowse")
    @AutoLog(operate = "删除用户浏览日志",identify = "管理员")
    public Result deletebrowse(@RequestBody BrowseTime browseTime)
    {
        int flag=browseService.deletebrowse(browseTime);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("浏览时间信息添加失败");
        }
    }
}
