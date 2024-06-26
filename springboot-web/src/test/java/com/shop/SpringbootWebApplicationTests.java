package com.shop;

import com.shop.entity.*;
import com.shop.mapper.BrowseMapper;
import com.shop.service.BrowseService;
import com.shop.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*import java.sql.Date;*/
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class SpringbootWebApplicationTests {
    @Autowired
    private BrowseService browseService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BrowseMapper browseMapper;

    @Test
    void contextLoads() {
    }

    //配置jwt
    @Test
    public void checkjwt()
    {
//        Key secreKey= Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        Map<String, Object> result;
        Map<String, Object> claims=new HashMap<>();
        claims.put("id",1);
        claims.put("username","singor");
//        //生成jwt令牌
//        String jwt=Jwts.builder()
//                .signWith(SignatureAlgorithm.HS256,"iuhuhknhuyguygnomnojmoijuhuygtyftyfytguoyguygtf")//签名算法
//                .setClaims(claims)//自定义内容
//                .setExpiration(new Date(System.currentTimeMillis()+12*3600*1000))//设置有效期为12小时
//                .compact();
    }

    public void par(String jwt)
    {
//        Map<String, Object> result;
//        //解析jwt令牌
//        result=Jwts.parserBuilder()
//                .setSigningKey("iuhuhknhuyguygnomnojmoijuhuygtyftyfytguoyguygtf")
//                .build()
//                .parseClaimsJws(jwt)
//                .getBody();
    }

    @Test
    public void showtime()
    {
        //yyyy-MM-dd HH:mm:ss
        Date time=new Date();
        Timestamp t = new Timestamp(time.getTime());
        System.out.println(t);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String birthday = df.format(time);
        System.out.println(birthday);

/*        //yyyy-MM-dd
        // 获取当前的日期和时间
        java.util.Date currentDate = new java.util.Date();
        // 将 util.Date 转换为 sql.Date
        Date sqlDate = new Date(currentDate.getTime());
        System.out.println("当前的 sql.Date: " + sqlDate);

        Date expireDate = new Date(System.currentTimeMillis());
        System.out.println(expireDate);*/
    }

    @Test
    public void show()
    {
//        User user=new User();
//        user.setUsername("root");
//        List<Goods> data=goodsService.getrecommendGoods(user);
//        System.out.println(data);
//        List<Integer> data=browseMapper.showclickgoods();
//        System.out.println(data);
//        User user=new User();
//        user.setUsername("");
//        if(Objects.equals(user.getUsername(), ""))
//        {
//            System.out.println("hello");
//        }
//        else {
//            System.out.println("err");
//        }
    }
}
