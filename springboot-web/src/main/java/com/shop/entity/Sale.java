package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    //销售日期
    private Date saledate;
    //用户购买次数
    private Integer buynum;
    //货物购买总数
    private Integer goodsnum;
    //当日总销售额
    private float totalprice;
}
