package com.shop.mapper;

import com.shop.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper //在运行时，会自动生成该接口的实现类对象，并将该对象交给IOC容器管理
public interface UserMapper
{
    //查询用户是否存在
    @Select("select * from web.user where username=#{username}")
    public User selectuser(User user);

    //根据用户名和密码查询信息
    @Select("select * from web.user where username=#{username} and password=#{password}")
    public User getmessage(User user);

    //添加用户信息
    @Insert("insert into web.user(username,password,email)"+
            "values(#{username},#{password},#{email})")
    public void insertmessage(User user);

    //删除用户信息
    @Delete("delete from web.user where username=#{username}")
    public void deleteaccount(User user);

    //修改用户信息
    @Update("update web.user set name=#{name},gender=#{gender},email=#{email} where username=#{username}")
    public void updateaccount(User user);

    //修改用户密码
    @Update("update web.user set password=#{password} where username=#{username}")
    public void updatepwd(User user);

    @Select("select email from web.user where username=#{username}")
    public String selectemail(String username);
}
