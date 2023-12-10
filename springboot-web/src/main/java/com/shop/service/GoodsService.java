package com.shop.service;

import com.shop.entity.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> showgoods();

    Goods selectgoods(Goods goods);

/*    String selectname(Integer id);*/

    int update(Goods goods);

    int addgoods(Goods goods);

    int deletegoods(Goods goods);
}
