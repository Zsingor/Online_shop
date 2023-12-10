package com.shop.service;

import com.shop.entity.Order;
import com.shop.entity.Sale;

import java.util.List;

public interface SaleService {

    int addsale(Order order);

    List<Sale> showsale();

    int deletesale(Sale sale);
}
