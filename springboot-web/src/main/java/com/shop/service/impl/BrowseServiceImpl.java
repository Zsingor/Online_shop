package com.shop.service.impl;

import com.shop.entity.BrowseTime;
import com.shop.mapper.BrowseMapper;
import com.shop.mapper.GoodsMapper;
import com.shop.mapper.UserMapper;
import com.shop.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class BrowseServiceImpl implements BrowseService {
    @Autowired
    private BrowseMapper browseMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserMapper userMapper;

    //获取所有用户的浏览信息
    @Override
    public List<BrowseTime> getbrowse() {
        return browseMapper.getbrowse();
    }

    //添加用户的浏览信息
    @Override
    public int addbrowse(BrowseTime browseTime) {
        int flag;
        try {
            Date time=new Date();
            Timestamp operatetime = new Timestamp(time.getTime());
            browseTime.setOperatetime(operatetime);
            browseMapper.addbrowse(browseTime);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    //删除用户的浏览信息
    @Override
    public int deletebrowse(BrowseTime browseTime) {
        int flag;
        try {
            browseMapper.deletebrowse(browseTime);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }
}
