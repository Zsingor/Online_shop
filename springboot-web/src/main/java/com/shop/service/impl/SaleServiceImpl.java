package com.shop.service.impl;

import com.shop.entity.Goods;
import com.shop.entity.Order;
import com.shop.entity.Sale;
import com.shop.mapper.GoodsMapper;
import com.shop.mapper.SaleMapper;
import com.shop.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Sale> showsale()
    {
        return saleMapper.showsale();
    }

    @Override
    public int addsale(Order order)
    {
        // 获取当前的日期和时间
        Date time=new Date();
        //转为数据库的date格式
        java.sql.Date sqlDate = new java.sql.Date(time.getTime());

        Sale sale=new Sale();
        Goods goods=new Goods();
        goods.setId(order.getGoodsid());
        sale.setSaledate(sqlDate);

        int flag;
        try
        {
            Sale saletemp=saleMapper.selecttime(sale);
            Goods goodstemp=goodsMapper.selectgoods(goods);
            if(saletemp!=null)
            {
                sale.setBuynum(saletemp.getBuynum()+1);
                sale.setGoodsnum(saletemp.getGoodsnum()+order.getNumber());
                sale.setTotalprice(saletemp.getTotalprice()+order.getNumber()*goodstemp.getPrice());
                saleMapper.updatesale(sale);
            }
            else {
                sale.setBuynum(1);
                sale.setGoodsnum(order.getNumber());
                sale.setTotalprice(order.getNumber()*goodstemp.getPrice());
                saleMapper.addsale(sale);
            }
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }

    @Override
    public int deletesale(@RequestBody Sale sale)
    {
        int flag;
        try {
            saleMapper.deletegoods(sale);
            flag=1;
        }
        catch (Exception error){
            flag=0;
        }
        return flag;
    }
}
