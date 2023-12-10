package com.shop.mapper;

import com.shop.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("select * from web.goods")
    public List<Goods> showgoods();

    @Select("select * from web.goods where id=#{id}")
    public Goods selectgoods(Goods goods);

    @Update("update web.goods set name=#{name},description=#{description},price=#{price},mainimg=#{mainimg} where id=#{id}")
    public void updategoods(Goods goods);

    @Insert("insert into web.goods(id,name,description,price,mainimg)"+
            "values(#{id},#{name},#{description},#{price},#{mainimg})")
    public void addgoods(Goods goods);

    //删除商品信息
    @Delete("delete from web.goods where id=#{id}")
    public void deletegoods(Goods goods);
}
