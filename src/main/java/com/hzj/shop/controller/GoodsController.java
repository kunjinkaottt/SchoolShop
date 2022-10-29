package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.GoodsMapper;
import com.hzj.shop.pojo.Goods;
import com.hzj.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoodsController {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GoodsService goodsService;

// 商家-商品管理-所有商品
    @GetMapping(value = {"/GoodsList/{currentPage}/{pageSize}/{shopid}/{search}","/GoodsList/{currentPage}/{pageSize}/{shopid}"})
    public Result<?> goodsList(@PathVariable("currentPage")Integer currentPage,
                               @PathVariable("pageSize")Integer pageSize,
                               @PathVariable("shopid")Integer shopid,
                               @PathVariable(value = "search",required=false)String search){
        return goodsService.goodsList(currentPage,pageSize,shopid,search);
    }

//普通用户-首页-店铺详情-店铺商品 包括该普通用户对店铺中商品的收藏情况
    @GetMapping(value = {"/MemberToGoodsList/{currentPage}/{pageSize}/{shopid}/{mid}/{search}",
                        "/MemberToGoodsList/{currentPage}/{pageSize}/{shopid}/{mid}"})
    public Result<?> MemberToGoodsList(@PathVariable("currentPage")Integer currentPage,
                               @PathVariable("pageSize")Integer pageSize,
                               @PathVariable("shopid")Integer shopid,
                               @PathVariable("mid")Integer mid,
                               @PathVariable(value = "search",required=false)String search){
        return goodsService.memberToGoodsList(currentPage,pageSize,shopid,mid,search);
    }

//商家、管理员-首页-店铺详情-店铺商品
    @GetMapping(value = {"/ToGoodsList/{currentPage}/{pageSize}/{shopid}/{search}","/ToGoodsList/{currentPage}/{pageSize}/{shopid}"})
    public Result<?> ToGoodsList(@PathVariable("currentPage")Integer currentPage,
                                   @PathVariable("pageSize")Integer pageSize,
                                   @PathVariable("shopid")Integer shopid,
                                   @PathVariable(value = "search",required=false)String search){
        return goodsService.toGoodsList(currentPage,pageSize,shopid,search);
    }

//    上传商品图片路径到商品表的img字段
//   商家-商品管理-所有商品 点击编辑 确定 操作》 更新商品信息
    @PutMapping("/updateGoods")
    public Result<?> updateGoods(@RequestBody Goods goods){
        return goodsService.updateGoods(goods);
    }

//  商家-商品管理-所有商品 点击 新增商品 确定 操作
    @PostMapping("/addNewGoods")
    public Result<?> addNewGoods(@RequestBody Goods goods){
        return goodsService.addNewGoods(goods);
    }

//  商家-商品管理-所有商品 点击 下架 操作
    @PutMapping("/deleteGoods")
    public Result<?> deleteGoods(@RequestBody Goods goods){
        return goodsService.deleteGoods(goods);
    }

//  商家-商品管理-商品分类
    @GetMapping("/goodstype/{shopid}")
    public Result<?> goodstype(@PathVariable("shopid")Integer shopid ){
        return goodsService.goodstype(shopid);
    }

}
