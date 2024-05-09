package com.shop.mapper;

import com.shop.entity.OperationLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper {

    @Select("select * from web.log order by operatedate desc")
    List<OperationLog> getlog();

    @Insert("insert into web.log(userid, identify, operate, ip, operatedate)"+
            " VALUE(#{userid},#{identify},#{operate},#{ip},#{operatedate})")
    void addlog(OperationLog operationLog);

    @Delete("delete from web.log where id=#{id}")
    void deletelog(OperationLog operationLog);
}
