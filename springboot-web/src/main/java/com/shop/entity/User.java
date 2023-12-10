package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
public class User
{
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String gender;
    private String email;
    private float money;
}
