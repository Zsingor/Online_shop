package com.shop.mapper;

import com.shop.entity.Goods;
import com.shop.entity.GoodsType;
import com.shop.entity.Sales;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("select * from web.goods")
    public List<Goods> showgoods();

    @Select("select * from web.goods where salesid=#{id}")
    public List<Goods> getgoods(Sales sales);

    @Select("select * from web.goods where id=#{id}")
    public Goods selectgoods(Goods goods);

    @Update("update web.goods set name=#{name},description=#{description},price=#{price},mainimg=#{mainimg} where id=#{id}")
    public void updategoods(Goods goods);

    @Insert("insert into web.goods(id,name,description,price,mainimg,salesid,type)"+
            "values(#{id},#{name},#{description},#{price},#{mainimg},#{salesid},#{type})")
    public void addgoods(Goods goods);

    //删除商品信息
    @Delete("delete from web.goods where id=#{id}")
    public void deletegoods(Goods goods);

    //查找对应销售员所负责商品的销售情况
    @Select("select g.type as type,sum(o.number) as number,sum(o.number*g.price) as price"+
            " from web.goods g join web.userorder o on g.id=o.goodsid"+
            " where g.salesid=#{id} group by g.type order by price desc limit 10 ")
    List<GoodsType> selecttype(Sales sales);

    //管理员查找所有商品的销售情况
    @Select("select g.type as type,sum(o.number) as number,sum(o.number*g.price) as price"+
            " from web.goods g join web.userorder o on g.id=o.goodsid"+
            " group by g.type order by price desc limit 10 ")
    List<GoodsType> selectalltype();

    //根据提供的id列表来获取对应的商品信息
    List<Goods> selectgoodsbyList(List<Integer> recommendgoods);

    //根据提供的id列表来获取其它的商品信息
    List<Goods> selectgoodsoutList(List<Integer> recommendgoods);
}
