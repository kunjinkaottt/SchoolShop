package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.pojo.dto.MembersWithShopDto;
import com.hzj.shop.pojo.dto.ShopABusinessListDto;
import com.hzj.shop.pojo.dto.ShopDetailDto;
import com.hzj.shop.pojo.dto.ShopTypeDto;
import com.hzj.shop.pojo.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
//该注解指这是一个mybatis 的 mapper类
@Mapper
*/
@Repository //代表持久层
public interface ShopMapper extends BaseMapper<Shop> {
//Page返回的结果 有data，其中有total查询出来的结果个数，有records 查询出来的结果列表，有size等等；而定义List，则查询出来的data就是列表的全部内容
    Page<ShopABusinessListDto> ShopABusinessList(Page<Shop> page,@Param("search")String search);//管理员首页、商家首页模糊查询

    Page<ShopABusinessListDto> BusinessHome(Page<Shop> page,@Param("shopid")Integer shopid);//商家首页
    //普通用户首页
    Page<MembersWithShopDto> membersHome(Page<Shop> page, @Param("search") String search, @Param("mid") Integer mid);
    //                                         ↑ Shop?  可以是MembersHomeDto吗
    //普通用户首页 新——卡片样式 可指定排序字段
    Page<MembersWithShopDto> membersHome2(Page<Shop> page, @Param("mid") Integer mid,@Param("sortItem")String sortItem, @Param("search") String search);

    //管理员-店铺管理 1营业 2休息
    Page<ShopABusinessListDto> ShopManage(Page<Shop> page,@Param("status") Integer status,@Param("search") String search);
    //管理员-店铺管理-待租的店铺
    Page<Shop> RentShopManage(Page<Shop> page,@Param("search")String search );

    List<ShopTypeDto> shoptype();//管理员-店铺管理-店铺分类

//    普通用户-首页-店铺类型
    List<MembersWithShopDto> MemberToShopDetail(@Param("mid") Integer mid,@Param("shopid") Integer shopid);
//    商家、管理员-首页-店铺类型
    List<ShopDetailDto> ToShopDetail(@Param("shopid") Integer shopid);

}
