package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.FavoritesMapper;
import com.hzj.shop.pojo.Favorites;
import com.hzj.shop.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class FavoritesController {

    @Autowired
    FavoritesMapper favoritesMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    FavoritesService favoritesService;

    //普通用户-首页 取消收藏（店铺）                或者 普通用户-收藏管理-收藏的店铺 取消收藏 操作
    //普通用户-首页-店铺详情_店铺商品 取消收藏(商品)  或者 普通用户-收藏管理-收藏的商品 取消收藏 操作
    @PutMapping("/cancelCollect/{fid}")
    public Result<?> cancelCollectShop(@PathVariable("fid") Integer fid) {
        return favoritesService.cancelCollect(fid);
    }

//    普通用户-收藏管理-收藏的商品 点击 收藏 操作(再次收藏！)
//    普通用户-收藏管理-收藏的店铺 点击 收藏 操作(再次收藏！)
    @PutMapping("/collectAgain/{fid}")
    public Result<?> collectAgain(@PathVariable("fid")Integer fid){
            return favoritesService.collectAgain(fid);
    }

    //普通用户-首页-查看 店铺详情界面 - 店铺商品- 收藏(商品)
    //普通用户-首页 收藏店铺
    @PostMapping("/collect")
    public Result<?> collect(@RequestBody Favorites favorites) {
        return favoritesService.collect(favorites);
    }

//    普通用户-收藏管理-收藏的店铺
    @GetMapping(value = {"/FavoriteShopList/{currentPage}/{pageSize}/{mid}","/FavoriteShopList/{currentPage}/{pageSize}/{mid}/{search}"})
public Result<?> FavoriteShopList(@PathVariable("currentPage")Integer currentPage,
                                  @PathVariable("pageSize")Integer pageSize,
                                  @PathVariable("mid")Integer mid,
                                  @PathVariable(value = "search",required = false)String search){
        return favoritesService.FavoriteShopList(currentPage,pageSize,mid,search);
    }

// 普通用户-收藏管理-收藏的商品
@GetMapping(value = {"/FavoriteGoodsList/{currentPage}/{pageSize}/{mid}","/FavoriteGoodsList/{currentPage}/{pageSize}/{mid}/{search}"})
    public Result<?> FavoriteGoodsList(@PathVariable("currentPage")Integer currentPage,
                                       @PathVariable("pageSize")Integer pageSize,
                                       @PathVariable("mid")Integer mid,
                                       @PathVariable(value = "search",required = false)String search){
    return favoritesService.FavoriteGoodsList(currentPage,pageSize,mid,search);
}

/*    @GetMapping("/ShopIsCollected/{mid}/{shopid}") //普通用户-店铺详情页面 判断该普通用户是否收藏了该店铺
    public Result<?> ShopIsCollected(@PathVariable("mid") Integer mid,
                                     @PathVariable("shopid") Integer shopid) {
        Favorites result = favoritesMapper.selectOne(Wrappers.<Favorites>lambdaQuery().eq(Favorites::getMid, mid).eq(Favorites::getShopid, shopid).eq(Favorites::getGid, 0).eq(Favorites::getIsfavorite, 1));
        if (result != null) {
            return Result.error("-1", "已收藏");
        } else
            return Result.success();
    }*/

}
