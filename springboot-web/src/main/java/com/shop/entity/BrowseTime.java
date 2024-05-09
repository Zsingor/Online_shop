package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrowseTime {
    private Integer id;
    private String username;
    private Integer goodsid;
    private Integer time;
    private Timestamp operatetime;
}
