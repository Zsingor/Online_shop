package com.shop.mapper;

import com.shop.entity.BrowseTime;
import com.shop.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BrowseMapper {
    @Select("select * from web.browsetime")
    List<BrowseTime> getbrowse();

    @Insert("insert into web.browsetime(username, goodsid, time, operatetime)"+
            "VALUE (#{username},#{goodsid},#{time},#{operatetime})")
    void addbrowse(BrowseTime browseTime);

    @Delete("delete from web.browsetime where id=#{id}")
    void deletebrowse(BrowseTime browseTime);

    //返回对应用户浏览过的ID列表
    @Select("select goodsid from web.browsetime where username=#{username} group by goodsid")
    List<Integer> selectgoods(User user);

    //返回点击率最高的商品的ID列表
    @Select("select goodsid from web.browsetime group by goodsid order by count(*) desc ")
    List<Integer> showclickgoods();
}
