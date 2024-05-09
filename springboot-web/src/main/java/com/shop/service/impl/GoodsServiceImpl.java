package com.shop.service.impl;

import com.shop.entity.Goods;
import com.shop.entity.GoodsType;
import com.shop.entity.Sales;
import com.shop.entity.User;
import com.shop.mapper.BrowseMapper;
import com.shop.mapper.GoodsMapper;
import com.shop.mapper.OrderMapper;
import com.shop.mapper.UserMapper;
import com.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BrowseMapper browseMapper;

    @Override
    public List<Goods> showgoods()
    {
        return goodsMapper.showgoods();
    }

    @Override
    public List<Goods> getgoods(Sales sales) {
        return goodsMapper.getgoods(sales);
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

    //销售员获得对应的商品销售信息
    @Override
    public List<Goods> goodssale(Sales sales) {
        List<Goods> data=goodsMapper.getgoods(sales);
        for (Goods datum : data)
        {
            Integer num = orderMapper.goodsnum(datum);
            if(num==null)
            {
                datum.setNumber(0);
                datum.setPrice(0);
            }
            else
            {
                float price = datum.getPrice();
                datum.setNumber(num);
                datum.setPrice(price * num);
            }
        }
        return data;
    }

    //管理员获得对应的商品销售信息
    @Override
    public List<Goods> goodsallsale() {
        List<Goods> data=goodsMapper.showgoods();
        for (Goods datum : data)
        {
            Integer num = orderMapper.goodsnum(datum);
            if(num==null)
            {
                datum.setNumber(0);
                datum.setPrice(0);
            }
            else
            {
                float price = datum.getPrice();
                datum.setNumber(num);
                datum.setPrice(price * num);
            }
        }
        return data;
    }

    //用于获取所有商品种类的销售状况
    @Override
    public List<GoodsType> selecttype(Sales sales) {
        return goodsMapper.selecttype(sales);
    }

    @Override
    public List<GoodsType> selectalltype() {
        return goodsMapper.selectalltype();
    }

    //获得推荐的商品列表
    @Override
    public List<Goods> getrecommendGoods(User user) {
        List<Integer> recommendgoods;
        //根据用户是否登录来制定不同的推荐方案
        //用户未登录，推荐点击率最高的商品信息
        if(user.getUsername()==null)
        {
            recommendgoods=browseMapper.showclickgoods();
        }
        else
        {
            //用户已登录，用协同过滤算法来推荐商品信息
            List<String> data=userMapper.showuser();
            recommendgoods= recommend(user.getUsername(),data);
        }
        List<Goods> res = new ArrayList<>();
        if(recommendgoods.size()>=5)
        {
            recommendgoods=recommendgoods.subList(0,5);
            res=goodsMapper.selectgoodsbyList(recommendgoods);
        }
        else
        {
            List<Goods> list1=goodsMapper.selectgoodsbyList(recommendgoods);
            List<Goods> list2=goodsMapper.selectgoodsoutList(recommendgoods);
            list2=list2.subList(0,5-recommendgoods.size());
            res.addAll(list1);
            res.addAll(list2);
        }
        return res;
    }

    //返回推荐的商品编号列表
    public List<Integer> recommend(String username, List<String> users) {
        //找到当前用户所浏览过的商品编号列表
        User u1 = new User();
        u1.setUsername(username);
        List<Integer> maingoods=browseMapper.selectgoods(u1);

        //找到相关系数最大的用户
        Map<Double, String> distances = computeNearestUser(username, maingoods, users);
        //找到map中double值最大的string
        String nearest= Collections.max(distances.entrySet(), Map.Entry.comparingByKey()).getValue();

        //找到最近用户浏览过，但是该用户没看过的商品，计算推荐
        User neighborRatings = new User();
        neighborRatings.setUsername(nearest);
        List<Integer> tempgoods=browseMapper.selectgoods(neighborRatings);

        //根据自己和相关用户的爱好计算推荐商品
        List<Integer> recommendationGoods = new ArrayList<>();
        for (Integer goods : tempgoods) {
            if (!maingoods.contains(goods)) {
                recommendationGoods.add(goods);
            }
        }
        Collections.sort(recommendationGoods);
        return recommendationGoods;
    }

    //找到与之最相近的用户
    private Map<Double, String> computeNearestUser(String username, List<Integer> maingoods, List<String> users) {
        Map<Double, String> distances = new TreeMap<>();

        User u1 = new User();
        u1.setUsername(username);

        User u2=new User();

        for (String name : users)
        {
            if (!name.equals(username))
            {
                u2.setUsername(name);
                List<Integer> tempgoods = browseMapper.selectgoods(u2);
                double distance = cosine_dis(maingoods, tempgoods);
                distances.put(distance, name);
            }

        }
        return distances;
    }

    //计算用户之间的余弦距离
    private double cosine_dis(List<Integer> maingoods, List<Integer> tempgoods) {
        Set<Integer> commonElements = new HashSet<>(maingoods);
        commonElements.retainAll(tempgoods);

        double dotProduct = commonElements.size();

        double length1 = Math.sqrt(maingoods.size());
        double length2 = Math.sqrt(tempgoods.size());

        if(length1*length2==0)
        {
            return 0;
        }

        return dotProduct / (length1 * length2);
    }
}
