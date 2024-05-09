package com.shop.service;


import com.shop.entity.Order;
import com.shop.entity.Sales;

import java.util.List;

public interface OrderService {
    public int addorder(Order order);

    public List<Order> selectorder();

    public List<Order> selectuserorder(Order order);

    List<Order> selectsalesorder(Sales sales);

    public int deleteorder(Order order);

    public int deletegoodsorder(Order order);

    public int deleteuserorder(String username);
}
