package com.shop.mapper;

import com.shop.entity.Goods;
import com.shop.entity.Order;
import com.shop.entity.Sales;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into web.userorder(username,goodsid,number,buytime,goodsname)"+
            "values(#{username},#{goodsid},#{number},#{buytime},#{goodsname})")
    public void addorder(Order order);

    @Select("select * from web.userorder ORDER BY buytime DESC")
    public List<Order> selectorder();

    @Select("select * from web.userorder where username=#{username} ORDER BY buytime DESC")
    public List<Order> selectuserorder(Order order);

    @Select("select * FROM web.userorder where goodsid IN (select id from web.goods WHERE salesid = #{id})")
    public List<Order> selectsalesorder(Sales sales);

    @Delete("delete from web.userorder where username=#{username} and goodsid=#{goodsid} and buytime=#{buytime}")
    public void deleteorder(Order order);

    @Delete("delete from web.userorder where goodsid=#{goodsid}")
    void deletegoodsorder(Order order);

    @Delete("delete from web.userorder where username=#{username}")
    public void deleteuserorder(String username);

    @Select("select sum(number) from userorder where goodsid=#{id}")
    Integer goodsnum(Goods goods);
}
