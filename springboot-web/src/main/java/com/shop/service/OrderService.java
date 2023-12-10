package com.shop.service;


import com.shop.entity.Order;

import java.util.List;

public interface OrderService {
    public int addorder(Order order);

    public List<Order> selectorder();

    public List<Order> selectuserorder(Order order);

    public int deleteorder(Order order);

    public int deleteuserorder(String username);
}
