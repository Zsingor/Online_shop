package com.shop.controller;

import com.shop.entity.OperationLog;
import com.shop.entity.Result;
import com.shop.service.LogService;
import com.shop.utility.AutoLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @PostMapping("/addlog")
    public Result addlog(@RequestBody OperationLog operationLog)
    {
        int flag=logService.addlog(operationLog);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("日志信息添加失败");
        }
    }

    @PostMapping("/getlog")
    @AutoLog(operate = "查看操作日志",identify = "管理员")
    public Result getlog()
    {
        try {
            List<OperationLog> data=logService.getlog();
            return Result.success(data);
        }
        catch (Exception error)
        {
            return Result.error("获取数据失败");
        }
    }

    @PostMapping("/deletelog")
    @AutoLog(operate = "删除操作日志",identify = "管理员")
    public Result deletelog(@RequestBody OperationLog operationLog)
    {
        int flag=logService.deletelog(operationLog);
        if(flag==1)
        {
            return Result.success();
        }
        else
        {
            return Result.error("日志信息删除失败");
        }
    }

}
