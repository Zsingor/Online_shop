package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private Integer id;
    private String name;
    private String description;
    private float price;
    private String mainimg;
    private Integer salesid;
    private String type;
    private Integer number;
}
