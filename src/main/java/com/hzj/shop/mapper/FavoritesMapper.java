package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.pojo.Favorites;
import com.hzj.shop.pojo.dto.FavoriteGoodsDto;
import com.hzj.shop.pojo.dto.FavoriteShopDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesMapper extends BaseMapper<Favorites> {
// 普通用户-收藏管理-收藏的店铺
    Page<FavoriteShopDto> FavoriteShopList(Page<FavoriteShopDto> page, @Param("mid")Integer mid,@Param("search")String search);
// 普通用户-收藏管理-收藏的商品
    Page<FavoriteGoodsDto> FavoriteGoodsList(Page<FavoriteGoodsDto> page,@Param("mid")Integer mid,@Param("search")String search);

    //普通用户-首页 取消收藏（店铺）                或者 普通用户-收藏管理-收藏的店铺 取消收藏 操作
    //普通用户-首页-店铺详情_店铺商品 取消收藏(商品)  或者 普通用户-收藏管理-收藏的商品 取消收藏 操作
    @Update("update favorites set isfavorite=0 where fid=#{fid}")
    void cancelCollect(int fid);

    //    普通用户-收藏管理-收藏的店铺 点击 收藏 操作(再次收藏！)
    //    普通用户-收藏管理-收藏的商品 点击 收藏 操作(再次收藏！)
    @Update("update favorites set isfavorite=1 where fid=#{fid}")
    void collectAgain(int fid);



}
