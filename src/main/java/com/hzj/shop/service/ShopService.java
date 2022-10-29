package com.hzj.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.ShopMapper;
import com.hzj.shop.pojo.Shop;
import com.hzj.shop.pojo.dto.MembersWithShopDto;
import com.hzj.shop.pojo.dto.ShopABusinessListDto;
import com.hzj.shop.pojo.dto.ShopDetailDto;
import com.hzj.shop.pojo.dto.ShopTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class ShopService {//业务层

    @Autowired
    ShopMapper shopMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 管理员-首页 或 商家-首页的模糊查询 2表+分页(+模糊查询) 店铺和商家列表
    public Result<?> ShopABusinessList(int currentPage,int pageSize,String search){
        Page<ShopABusinessListDto> ShopABusinessList = shopMapper.ShopABusinessList(new Page<>(currentPage, pageSize),search);
        return Result.success(ShopABusinessList);
    }

    //商家-首页  （!!!!!!默认置顶自己的店铺!!!!!!）
    public Result<?> BusinessShopList(int currentPage,int pageSize,int shopid){
        Page<ShopABusinessListDto> BusinessHome = shopMapper.BusinessHome(new Page<>(currentPage, pageSize),shopid);
        return  Result.success(BusinessHome);//有包括total(res.data.total)
    }

    //    普通用户-首页（包括模糊查询）——能显示平均分，收藏情况等
    public Result<?> membersHome(int currentPage,int pageSize,int mid,String search){
        Page<MembersWithShopDto> membersHome = shopMapper.membersHome(new Page<>(currentPage, pageSize),search,mid);
        return  Result.success(membersHome);//有包括total(res.data.total)
    }
    //    普通用户-首页 新——卡片样式 选择排序
    public Result<?> membersHome2(int currentPage,int pageSize,int mid,String sortItem,String search){
        Page<MembersWithShopDto> membersHome = shopMapper.membersHome2(new Page<>(currentPage, pageSize),mid,sortItem,search);
        return  Result.success(membersHome);//有包括total(res.data.total)
    }


    //管理员-店铺管理——传的参包括店铺状态status 1 2
    public Result<?> ShopManage(int currentPage,int pageSize,int status,String search ){
        Page<ShopABusinessListDto> ShopManage = shopMapper.ShopManage(new Page<>(currentPage,pageSize),status,search);
        return Result.success(ShopManage);
    }
    //管理员-店铺管理-待租的店铺
    public Result<?> RentShopManage(int currentPage,int pageSize, String search ){
        Page<Shop> rentShopManage = shopMapper.RentShopManage(new Page<>(currentPage,pageSize),search);
        return Result.success(rentShopManage);
    }

    //根据id查询一条店铺记录  用于商家-店铺管理-我的店铺界面和 查看店铺时的店铺详情界面
    public Shop MyShop(int shopid){
        return shopMapper.selectById(shopid);
    }

    //普通用户-首页-查看-店铺详情 包括该普通用户对店铺的收藏情况
    public Result<?> MemberToShopDetail(int mid ,int shopid){
        List<MembersWithShopDto> membersWithShopDtos = shopMapper.MemberToShopDetail(mid, shopid);
        return  Result.success(membersWithShopDtos);//有包括total(res.data.total)
    }
    //商家、管理员-首页-查看-店铺详情
    public Result<?> ToShopDetail(int shopid){
        List<ShopDetailDto> shopDetailDtos = shopMapper.ToShopDetail(shopid);
        return  Result.success(shopDetailDtos);
    }

//管理员-首页  新增店铺 或编辑 待租 店铺
    public Result<?> NewShop(@RequestBody Shop shop){//能不能去掉@RequestBody
        if(shop.getAddress()==null||shop.getAddress().equals("")){
            return Result.error("-1","地址不能为空！");
        }else if(shop.getShopid()==0){
            shopMapper.insert(shop);
            return Result.success("新增店铺成功");    //添加成功 返回 Result类的成功方法
        }else {
            shopMapper.updateById(shop);
            return Result.success("更新地址成功");
        }
    }

    //管理员-首页 删除待租的店铺
    public Result<?> deleteNewShop(int shopid){
        shopMapper.deleteById(shopid);
        String sql="update business set state=2 where shopid="+shopid;//所有申请该店铺的商家注册都不通过,并且business表中的shopid赋为0
        jdbcTemplate.update(sql);
        return Result.success();
    }

    //管理员-首页 收回店铺
    public Result<?> recoverShop(int shopid){
        String qeuryBidSql =            "select bid from shop where shopid="+shopid;
        Map<String, Object> bidMap = jdbcTemplate.queryForMap(qeuryBidSql);
        System.out.println("该被收回店铺的商家id"+bidMap.get("bid"));
        String recoveryShopSql =        "update shop set status=3,photo='',shopname=address,introduce='',openinghours='',shoptype='其他',bid=0,recruit=''  where shopid="+ shopid ;
        String updateBusinessStateSql = "update business set state=2 where bid="+ bidMap.get("bid");
        String deleteStaffSql =         "delete from staff where shopid="+shopid;  //删除该店铺员工
        String deleteApplySql =         "delete from apply where shopid="+shopid;  //删除该店铺应聘申请
        String deleteComments =         "delete from comment where shopid = "+shopid; //删除该店铺的所有评论
        String deleteGoods =            "update goods set isdelete = 1  where shopid = "+shopid; //逻辑删除该店铺的所有商品
        String deleteFavorites =        "update favorites set isfavorite = 0  where shopid = "+shopid; //取消该店铺的所有收藏
        jdbcTemplate.batchUpdate(recoveryShopSql,updateBusinessStateSql,deleteStaffSql,deleteApplySql,deleteComments,deleteGoods,deleteFavorites);
        return Result.success();
    }

    //商家-店铺管理-我的店铺-点击 保存修改按钮
    public Result<?> updateMyShop(Shop shop) {
        if (shop.getStatus()==3){
            return Result.error("-1","操作失败，请重启");
        }else {
            shopMapper.updateById(shop);
            return Result.success();
        }
    }

    //管理员-店铺管理-店铺分类(店铺类型的分类) 获取各店铺类型的个数
    public Result<?> shoptype() {
        List<ShopTypeDto> shoptype = shopMapper.shoptype();
        return Result.success(shoptype);//shopMapper.shoptype()一个店铺类型(ShopTypeDto) 列表
    }

    //管理员-店铺管理-店铺分类(店铺状态的分类) 获取各店铺状态的个数
    public Result<?> statustype() {
        String sql ="select count(shopid) count, status from shop GROUP BY status";
        List<Map<String, Object>> shoptypelist = jdbcTemplate.queryForList(sql);
        return  Result.success(shoptypelist);
    }

}
