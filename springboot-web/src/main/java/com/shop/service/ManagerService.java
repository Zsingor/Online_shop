package com.shop.service;

import com.shop.entity.Manager;

public interface ManagerService {
    //管理员登录
    Manager login(Manager manager);

    //管理员注册
    String register(Manager manager);

    //管理员注销
    int delete(Manager manager);
}
