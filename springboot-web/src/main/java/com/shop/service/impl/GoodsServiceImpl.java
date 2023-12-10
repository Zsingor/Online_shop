package com.shop.service.impl;

import com.shop.entity.Goods;
import com.shop.mapper.GoodsMapper;
import com.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> showgoods()
    {
        return goodsMapper.showgoods();
    }

    @Override
    public Goods selectgoods(Goods goods)
    {
        return goodsMapper.selectgoods(goods);
    }

    @Override
    public int update(Goods goods)
    {
        int flag;
        try {
            goodsMapper.updategoods(goods);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public int addgoods(Goods goods)
    {
        int flag;
        try {
            goodsMapper.addgoods(goods);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public int deletegoods(Goods goods)
    {
        int flag;
        try {
            goodsMapper.deletegoods(goods);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }
}
