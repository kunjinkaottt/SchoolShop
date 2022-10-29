package com.hzj.shop;

import com.hzj.shop.mapper.BusinessMapper;
import com.hzj.shop.mapper.ShopMapper;
import com.hzj.shop.pojo.Business;
import com.hzj.shop.pojo.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ShopApplicationTests {

    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
/*
        String sql = "select s.photo,s.shopname,b.truename,b.bphone,s.shoptype,s.status,s.address " +
                "from shop s,business b " +
                "where s.shopid=b.shopid " +
                "order by status";  //,convert(s.shopname using gbk) collate gbk_chinese_ci desc
        List<Map<String, Object>> list_map = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> stringObjectMap : list_map) {
            System.out.println(stringObjectMap);
        }
*/

        String sql = "select s.photo,s.shopname,b.truename businessName,b.bphone,s.shoptype,s.status,s.address " +
                "from shop s left join business b on s.bid=b.bid " +
                "order by s.status,convert(s.shopname using gbk) collate gbk_chinese_ci";  //,convert(s.shopname using gbk) collate gbk_chinese_ci 按拼音首字母排序
        List<Map<String, Object>> list_map = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> stringObjectMap : list_map) {
            System.out.println(stringObjectMap);
        }


        String qeuryBidSql ="select bid from shop where shopid="+1;
        Map<String, Object> bidMap = jdbcTemplate.queryForMap(qeuryBidSql);
        System.out.println(bidMap.get("bid"));

    }



}
