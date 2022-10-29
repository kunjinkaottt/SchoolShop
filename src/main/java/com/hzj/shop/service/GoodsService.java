package com.hzj.shop.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.GoodsMapper;
import com.hzj.shop.mapper.ShopMapper;
import com.hzj.shop.pojo.Goods;
import com.hzj.shop.pojo.Shop;
import com.hzj.shop.pojo.dto.GoodsTypeDto;
import com.hzj.shop.pojo.dto.MemberToGoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 商家-商品管理-所有商品
    public Result<?> goodsList(int currentPage,int pageSize,int shopid,String search){
        Page<Goods> goodsList = goodsMapper.goodsList(new Page<>(currentPage,pageSize),shopid,search);
        return Result.success(goodsList);
    }

    //普通用户-首页-店铺详情-店铺商品 包括该普通用户对店铺中商品的收藏情况
    public Result<?> memberToGoodsList(int currentPage,int pageSize,int shopid,int mid,String search){
        Page<MemberToGoodsDto> memberToGoodsList = goodsMapper.memberToGoodsList(new Page<>(currentPage,pageSize),shopid,mid,search);
        return Result.success(memberToGoodsList);
    }

    //商家、管理员-首页-店铺详情-店铺商品
    public Result<?> toGoodsList(int currentPage,int pageSize,int shopid,String search){
        Page<Goods> toGoodsList = goodsMapper.toGoodsList(new Page<>(currentPage,pageSize),shopid,search);
        return Result.success(toGoodsList);
    }

    //    上传商品图片路径到商品表的img字段
//   商家-商品管理-所有商品 点击编辑 确定 操作》 更新商品信息
    public Result<?> updateGoods(Goods goods){
        System.out.println("更新的goods"+goods);
        goodsMapper.updateById(goods);
        return Result.success();
    }

    //  商家-商品管理-所有商品 点击 新增商品 确定 操作
    public Result<?> addNewGoods(Goods goods){
        Shop shop = shopMapper.selectOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getShopid, goods.getShopid()));
        if (shop.getStatus()==3){
            return Result.error("-1","操作失败，请重启");
        } else {
            goodsMapper.insert(goods);
            return Result.success();
        }
    }

    //  商家-商品管理-所有商品 点击 下架 操作
    public Result<?> deleteGoods(Goods goods){
        System.out.println("删除的goods"+goods);
        goodsMapper.updateById(goods);
        return Result.success();
    }

    //  商家-商品管理-商品分类
    public Result<?> goodstype(int shopid ){
        List<GoodsTypeDto> goodstype = goodsMapper.goodstype(shopid);
        System.out.println(goodstype);
        return Result.success(goodstype);
    }

}
