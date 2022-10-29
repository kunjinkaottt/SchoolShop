package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.pojo.dto.GoodsTypeDto;
import com.hzj.shop.pojo.dto.MemberToGoodsDto;
import com.hzj.shop.pojo.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
//  商家-商品管理-所有商品 （获取所有商品列表）
    Page<Goods> goodsList(Page<Goods> page, @Param("shopid")Integer shopid,@Param("search")String search);
//  商家-商品管理-商品分类
    List<GoodsTypeDto> goodstype(@Param("shopid")Integer shopid);//管理员-店铺管理-店铺分类
//  普通用户-首页-查看（店铺详情界面）-所有商品
    Page<MemberToGoodsDto> memberToGoodsList(Page<MemberToGoodsDto> page,@Param("shopid")Integer shopid,@Param("mid")Integer mid,@Param("search")String search);
//商家、管理员-首页-店铺详情-所有商品
    Page<Goods> toGoodsList(Page<Goods> page,@Param("shopid")Integer shopid,@Param("search")String search);

}
