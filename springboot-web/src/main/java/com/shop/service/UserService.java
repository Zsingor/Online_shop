package com.shop.service;

import com.shop.entity.User;

public interface UserService {
    //验证用户的用户名或密码是否正确
    User login(User user);

    //用户注册
    String register(User user);

    //查询用户信息
    User select(User user);

    //用户注销
    int delete(User user);

    //用户修改
    int updatecount(User user);

    //验证密码
    int checkpwd(User user);

    //修改密码
    int updatepwd(User user);

    //查找用户邮箱
    String selectemail(String username);
}
