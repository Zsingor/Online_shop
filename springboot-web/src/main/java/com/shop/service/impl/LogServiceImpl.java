package com.shop.service.impl;

import com.shop.entity.OperationLog;
import com.shop.mapper.LogMapper;
import com.shop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    //查询所有的日志信息
    @Override
    public List<OperationLog> getlog() {
        return logMapper.getlog();
    }

    //添加日志信息
    @Override
    public int addlog(OperationLog operationLog) {
        int flag;
        try
        {
            Date time=new Date();
            Timestamp operatetime = new Timestamp(time.getTime());
            operationLog.setOperatedate(operatetime);
            logMapper.addlog(operationLog);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    //删除相关日志
    @Override
    public int deletelog(OperationLog operationLog) {
        int flag;
        try {
            logMapper.deletelog(operationLog);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }
}
