package com.shop.controller;

import com.shop.entity.Result;
import com.shop.entity.Sale;
import com.shop.service.SaleService;
import com.shop.utility.AutoLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SaleController {
    @Autowired
    private SaleService saleService;

    @PostMapping("/showsale")
    @AutoLog(operate = "查看每日销售",identify = "管理员")
    public Result showsale()
    {
        List<Sale> data=saleService.showsale();
        return Result.success(data);
    }

    @PostMapping("/deletesale")
    @AutoLog(operate = "查看每日销售",identify = "销售员")
    public Result deletesale(@RequestBody Sale sale)
    {
        int flag=saleService.deletesale(sale);
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
