package com.shop.service;

import com.shop.entity.OperationLog;

import java.util.List;

public interface LogService {
    List<OperationLog> getlog();

    int addlog(OperationLog operationLog);

    int deletelog(OperationLog operationLog);
}
