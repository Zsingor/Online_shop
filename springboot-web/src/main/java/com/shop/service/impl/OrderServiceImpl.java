package com.shop.service.impl;

import com.shop.entity.Order;
import com.shop.mapper.GoodsMapper;
import com.shop.mapper.OrderMapper;
import com.shop.mapper.SaleMapper;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int addorder(Order order)
    {
        Date time=new Date();
        Timestamp buytime = new Timestamp(time.getTime());
        order.setBuytime(buytime);
        int flag;
        try {
            orderMapper.addorder(order);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public List<Order> selectorder()
    {
        return orderMapper.selectorder();
    }

    @Override
    public List<Order> selectuserorder(Order order)
    {
        return orderMapper.selectuserorder(order);
    }

    @Override
    public int deleteorder(Order order)
    {
        int flag;
        try {
            orderMapper.deleteorder(order);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public int deleteuserorder(String username)
    {
        int flag;
        try {
            orderMapper.deleteuserorder(username);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }
}
