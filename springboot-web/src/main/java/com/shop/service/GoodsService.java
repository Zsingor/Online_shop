package com.shop.service;

import com.shop.entity.Goods;
import com.shop.entity.GoodsType;
import com.shop.entity.Sales;
import com.shop.entity.User;

import java.util.List;

public interface GoodsService {
    List<Goods> showgoods();

    List<Goods> getgoods(Sales sales);

    Goods selectgoods(Goods goods);

    int update(Goods goods);

    int addgoods(Goods goods);

    int deletegoods(Goods goods);

    List<Goods> goodssale(Sales sales);

    List<Goods> goodsallsale();

    List<GoodsType> selecttype(Sales sales);

    List<GoodsType> selectalltype();

    List<Goods> getrecommendGoods(User user);
}
