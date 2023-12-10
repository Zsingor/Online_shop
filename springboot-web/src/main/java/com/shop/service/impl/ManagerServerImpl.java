package com.shop.service.impl;

import com.shop.entity.Manager;
import com.shop.entity.User;
import com.shop.mapper.ManagerMapper;
import com.shop.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServerImpl implements ManagerService
{
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Manager login(Manager manager)
    {
        return managerMapper.getmessage(manager);
    }

    @Override
    public String register(Manager manager)
    {
        Manager temp=managerMapper.selectuser(manager);
        if(temp!=null)
        {
            return "1";
        }
        else
        {
            managerMapper.insertmessage(manager);
            return "0";
        }
    }

    @Override
    public int delete(Manager manager)
    {
        int flag;
        try {
            managerMapper.deleteaccount(manager);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }
}
