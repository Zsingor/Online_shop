package com.shop.service.impl;

import com.shop.entity.User;
import com.shop.mapper.UserMapper;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServerImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user)
    {
        return userMapper.getmessage(user);
    }

    @Override
    public String register(User user)
    {
        User temp=userMapper.selectuser(user);
        if(temp!=null)
        {
            return "1";
        }
        else
        {
            userMapper.insertmessage(user);
            return "0";
        }
    }

    @Override
    public User select(User user)
    {
        return userMapper.selectuser(user);
    }

    @Override
    public int delete(User user)
    {
        int flag;
        try {
            userMapper.deleteaccount(user);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public int updatecount(User user) {
        int flag;
        try {
            userMapper.updateaccount(user);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public int checkpwd(User user)
    {
        int flag;
        User temp=userMapper.getmessage(user);
        if(temp!=null)
        {
            flag=1;
        }
        else
        {
            flag=0;
        }
        return flag;
    }

    @Override
    public int updatepwd(User user)
    {
        int flag;
        try {
            userMapper.updatepwd(user);
            flag=1;
        }
        catch (Exception error)
        {
            flag=0;
        }
        return flag;
    }

    @Override
    public String selectemail(String username)
    {
        return userMapper.selectemail(username);
    }
}
