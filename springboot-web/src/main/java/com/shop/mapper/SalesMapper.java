package com.shop.mapper;

import com.shop.entity.GoodsType;
import com.shop.entity.Sales;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SalesMapper {
    @Select("select * from web.sales where id=#{id} and password=#{password}")
    Sales getmessage(Sales sales);

    //查询用户是否存在
    @Select("select * from web.sales where id=#{id}")
    Sales selectuser(Sales sales);

    //插入用户数据
    @Insert("insert into web.sales(id,password)"+
            "values(#{id},#{password})")
    void insertmessage(Sales sales);

    //删除用户信息
    @Delete("delete from web.sales where id=#{id}")
    void deleteaccount(Sales sales);

    //获取所有销售员信息
    @Select("select * from web.sales")
    List<Sales> showsales();

    //重置销售员的密码
    @Update("update web.sales set password=#{password} where id=#{id}")
    public void updatesales(Sales sales);

    //管理员查找所有销售员的销售情况
    @Select("select g.salesid as type,sum(o.number) as number,sum(o.number*g.price) as price"+
            " from web.goods g join web.userorder o on g.id=o.goodsid"+
            " group by g.salesid order by price desc ")
    List<GoodsType> selectsales();
}
