package com.shop.service.impl;

import com.shop.entity.GoodsType;
import com.shop.entity.Sales;
import com.shop.mapper.SalesMapper;
import com.shop.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private SalesMapper salesMapper;

    @Override
    public Sales login(Sales sales)
    {
        return salesMapper.getmessage(sales);
    }

    @Override
    public String register(Sales sales) {
        Sales temp=salesMapper.selectuser(sales);
        if(temp!=null)
        {
            return "1";
        }
        else
        {
            salesMapper.insertmessage(sales);
            return "0";
        }
    }

    @Override
    public Sales select(Sales sales) {
        return salesMapper.selectuser(sales);
    }

    @Override
    public int delete(Sales sales) {
        int flag;
        try {
            salesMapper.deleteaccount(sales);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    //销售人员口令重置
    @Override
    public int update(Sales sales) {
        int flag;
        try {
            sales.setPassword("12345678");
            salesMapper.updatesales(sales);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public List<Sales> showsales() {
        return salesMapper.showsales();
    }

    //查询所有销售员的销售情况
    @Override
    public List<GoodsType> salesorder() {
        return salesMapper.selectsales();
    }
}
