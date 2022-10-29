package com.hzj.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.FavoritesMapper;
import com.hzj.shop.pojo.Favorites;
import com.hzj.shop.pojo.dto.FavoriteGoodsDto;
import com.hzj.shop.pojo.dto.FavoriteShopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FavoritesService {

    @Autowired
    FavoritesMapper favoritesMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //普通用户-首页 取消收藏（店铺）                或者 普通用户-收藏管理-收藏的店铺 取消收藏 操作
    //普通用户-首页-店铺详情_店铺商品 取消收藏(商品)  或者 普通用户-收藏管理-收藏的商品 取消收藏 操作
    public Result<?> cancelCollect(int fid) {
        System.out.println("取消的收藏fid："+fid);
        favoritesMapper.cancelCollect(fid);
        return Result.success();
    }

    //    普通用户-收藏管理-收藏的店铺 点击 收藏 操作(再次收藏！)
    //    普通用户-收藏管理-收藏的商品 点击 收藏 操作(再次收藏！)
    public Result<?> collectAgain(int fid){
        System.out.println("再次收藏的收藏fid："+fid);
        favoritesMapper.collectAgain(fid);
        return Result.success();
    }

    //  普通用户-首页-查看 店铺详情界面 - 店铺商品- 收藏(商品)
    //  普通用户-首页 收藏店铺
    public Result<?> collect(Favorites favorites) {
        System.out.println("店铺收藏对象 favorites"+favorites);
        if(favorites.getFid()==0){  //此前无收藏过
            favoritesMapper.insert(favorites);
        }else {         //此前已有收藏记录
            favoritesMapper.updateById(favorites);
        }
        return Result.success();
    }

    // 普通用户-收藏管理-收藏的店铺
    public Result<?> FavoriteShopList(int currentPage,int pageSize,int mid,String search){
        Page<FavoriteShopDto> FavoriteShopList = favoritesMapper.FavoriteShopList(new Page<>(currentPage,pageSize),mid,search);
        return Result.success(FavoriteShopList);
    }

    // 普通用户-收藏管理-收藏的商品
    public Result<?> FavoriteGoodsList(int currentPage,int pageSize,int mid,String search){
        Page<FavoriteGoodsDto> FavoriteGoodsList = favoritesMapper.FavoriteGoodsList(new Page<>(currentPage,pageSize),mid,search);
        return Result.success(FavoriteGoodsList);
    }

}
