package com.shop.mapper;

import com.shop.entity.Sale;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SaleMapper {
    @Select("select * from web.sale where saledate=#{saledate}")
    public Sale selecttime(Sale sale);

    @Insert("insert into web.sale(saledate,buynum,goodsnum,totalprice)"+
            "values(#{saledate},#{buynum},#{goodsnum},#{totalprice})")
    public void addsale(Sale sale);

    @Update("update web.sale set buynum=#{buynum},goodsnum=#{goodsnum},totalprice=#{totalprice} where saledate=#{saledate}")
    public void updatesale(Sale sale);

    @Select("select * from web.sale ORDER BY saledate DESC")
    public List<Sale> showsale();

    @Delete("delete from web.sale where saledate=#{saledate}")
    public void deletegoods(Sale sale);
}
