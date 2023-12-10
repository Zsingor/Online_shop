package com.shop.mapper;

import com.shop.entity.Manager;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagerMapper {
    //根据用户名和密码查询信息
    @Select("select * from web.manager where managername=#{managername} and password=#{password}")
    public Manager getmessage(Manager manager);

    //查询用户是否存在
    @Select("select * from web.manager where managername=#{managername}")
    public Manager selectuser(Manager manager);

    //插入用户数据
    @Insert("insert into web.manager(managername,password)"+
            "values(#{managername},#{password})")
    public void insertmessage(Manager manager);

    //删除用户信息
    @Delete("delete from web.manager where managername=#{managername}")
    public void deleteaccount(Manager manager);
}
