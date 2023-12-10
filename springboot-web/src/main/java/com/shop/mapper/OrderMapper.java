package com.shop.mapper;

import com.shop.entity.Order;
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

    @Delete("delete from web.userorder where username=#{username} and goodsid=#{goodsid} and buytime=#{buytime}")
    public void deleteorder(Order order);

    @Delete("delete from web.userorder where username=#{username}")
    public void deleteuserorder(String username);
}
