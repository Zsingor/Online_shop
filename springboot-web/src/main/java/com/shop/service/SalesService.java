package com.shop.service;

import com.shop.entity.GoodsType;
import com.shop.entity.Sales;

import java.util.List;

public interface SalesService {
    //销售员登录
    Sales login(Sales sales);

    //销售员注册
    String register(Sales sales);

    //查找销售员
    Sales select(Sales sales);

    //销售员注销
    int delete(Sales sales);

    //销售人员口令重置
    int update(Sales sales);

    //获取所有销售员信息
    List<Sales> showsales();

    //查询所有销售员的销售情况
    List<GoodsType> salesorder();
}
