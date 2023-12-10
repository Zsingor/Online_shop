package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order
{
    private String username;
    private Integer goodsid;
    private String goodsname;
    private Integer number;
    private Timestamp buytime;
}
