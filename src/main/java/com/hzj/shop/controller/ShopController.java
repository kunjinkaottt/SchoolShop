package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.ShopMapper;
import com.hzj.shop.pojo.Shop;
import com.hzj.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private ShopService shopService;

    // 管理员-首页 或 商家-首页的模糊查询 2表+分页(+模糊查询) 店铺和商家列表
    @GetMapping(value = {"/ShopABusinessList/{currentPage}/{pageSize}/{search}","/ShopABusinessList/{currentPage}/{pageSize}"})
    public Result<?> ShopABusinessList(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                       @PathVariable("pageSize") Integer pageSize, //默认值 每页10条记录（ pageSize每页多少条记录）
                                       @PathVariable(value = "search",required = false) String search    ){//required = false非必要的
        return shopService.ShopABusinessList(currentPage, pageSize, search);
    }

    //商家-首页  （!!!!!!默认置顶自己的店铺!!!!!!）
    @GetMapping("/BusinessHome/{currentPage}/{pageSize}/{shopid}")
    public Result<?> BusinessShopList(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                                @PathVariable("pageSize") Integer pageSize,//默认值 每页10条记录（ pageSize每页多少条记录）
                                                @PathVariable("shopid") Integer shopid){
        return shopService.BusinessShopList(currentPage, pageSize, shopid);
    }

    //    普通用户-首页（包括模糊查询）——能显示平均分，收藏情况等
    @GetMapping(value = {"/MembersHome/{currentPage}/{pageSize}/{mid}/{search}","/MembersHome/{currentPage}/{pageSize}/{mid}"})//！！！设置2个请求路径，一个有模糊查询一个没有！！！
    public Result<?> membersHome(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                 @PathVariable("pageSize") Integer pageSize, //默认值 每页10条记录（ pageSize每页多少条记录）
                                 @PathVariable("mid")Integer mid,       //restful风格的URL采用PathVariable，请求url?id=xxx原始URL风格采用RequestParam
                                 @PathVariable(value = "search" , required = false) String search    ){//required = false非必要的
        return shopService.membersHome(currentPage, pageSize, mid, search);
    }
    //    普通用户-首页      新——卡片样式 选择排序
    @GetMapping(value = {"/MembersHome2/{currentPage}/{pageSize}/{mid}/{sortItem}/{search}","/MembersHome2/{currentPage}/{pageSize}/{mid}/{sortItem}"})//设置2个请求路径，一个有模糊查询一个没有
    public Result<?> membersHome2(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                 @PathVariable("pageSize") Integer pageSize, //默认值 每页10条记录（ pageSize每页多少条记录）
                                 @PathVariable("mid")Integer mid,       //restful风格的URL采用PathVariable，请求url?id=xxx原始URL风格采用RequestParam
                                 @PathVariable("sortItem") String sortItem,
                                 @PathVariable(value = "search" , required = false) String search){//required = false非必要的
        return shopService.membersHome2(currentPage, pageSize, mid,sortItem, search);
    }

    //管理员-店铺管理——传的参包括店铺状态status 1 2
    @GetMapping(value = {"/ShopManage/{currentPage}/{pageSize}/{status}","/ShopManage/{currentPage}/{pageSize}/{status}/{search}"})
    public Result<?> ShopManage(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                @PathVariable("pageSize") Integer pageSize,
                                @PathVariable("status") Integer status,
                                @PathVariable(value = "search",required = false) String search ){//默认值 每页10条记录（ pageSize每页多少条记录）
        return shopService.ShopManage(currentPage, pageSize, status, search);
    }
    //管理员-店铺管理-待租的店铺
    @GetMapping(value = {"/RentShopManage/{currentPage}/{pageSize}","/RentShopManage/{currentPage}/{pageSize}/{search}"})
    public Result<?> RentShopManage(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                @PathVariable("pageSize") Integer pageSize,
                                @PathVariable(value = "search",required = false) String search ){//默认值 每页10条记录（ pageSize每页多少条记录）
        return shopService.RentShopManage(currentPage, pageSize, search);
    }

    //根据id查询一条店铺记录  用于商家-店铺管理-我的店铺界面和 查看店铺时的店铺详情界面
    @GetMapping("/MyShop/{shopid}")
    public Shop MyShop(@PathVariable("shopid") Integer shopid){
        return shopService.MyShop(shopid);
    }

    //普通用户-首页-查看-店铺详情 包括该普通用户对店铺的收藏情况
    @GetMapping("/MemberToShopDetail/{mid}/{shopid}")
    public Result<?> MemberToShopDetail(@PathVariable("mid") Integer mid ,
                                        @PathVariable("shopid") Integer shopid){
        return shopService.MemberToShopDetail(mid, shopid);
    }
    //商家、管理员-首页-查看-店铺详情
    @GetMapping("/ToShopDetail/{shopid}")
    public Result<?> ToShopDetail(@PathVariable("shopid") Integer shopid){
        return shopService.ToShopDetail(shopid);
    }

    //管理员-首页  新增店铺 或者 编辑 待租店铺
    @PostMapping("/NewShop")
    public  Result<?> NewShop(@RequestBody Shop shop){
        return shopService.NewShop(shop);
    }

    //管理员 删除待租的店铺
    @DeleteMapping("/deleteNewShop/{shopid}")
    public Result<?> deleteNewShop(@PathVariable("shopid") Integer shopid){
        return shopService.deleteNewShop(shopid);
    }

    //管理员 收回店铺
    @PutMapping("/recoverShop/{shopid}")
    public Result<?> recoverShop(@PathVariable("shopid") Integer shopid){
        return shopService.recoverShop(shopid);
    }

    //商家-店铺管理-我的店铺-点击 保存修改按钮
    @PutMapping("/updateMyShop")
    public Result<?> updateMyShop(@RequestBody Shop shop) {
        return shopService.updateMyShop(shop);
    }

    //管理员-店铺管理-店铺分类(店铺类型的分类) 获取各店铺类型的个数
    @GetMapping("/shoptype")
    public Result<?> shoptype() {
        return shopService.shoptype();//一个店铺类型(ShopTypeDto) 列表
    }

    //管理员-店铺管理-店铺分类(店铺状态的分类) 获取各店铺状态的个数
    @GetMapping("/statustype")
    public Result<?> statustype() {
        return  shopService.statustype();
    }

}
