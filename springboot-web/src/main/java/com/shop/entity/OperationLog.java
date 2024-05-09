package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {
    private Integer id;
    private String userid;
    private String identify;
    private String operate;
    private String ip;
    private Timestamp operatedate;
}
