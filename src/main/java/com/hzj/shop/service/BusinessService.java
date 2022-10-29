package com.hzj.shop.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.BusinessMapper;
import com.hzj.shop.mapper.ShopMapper;
import com.hzj.shop.pojo.Business;
import com.hzj.shop.pojo.Shop;
import com.hzj.shop.pojo.dto.BusinessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class BusinessService {  //业务层

    @Autowired
    BusinessMapper businessMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ShopMapper shopMapper;

    //管理员-审核管理-未审核的商家申请
    public Result<?> ExamineBusinessList(int currentPage, int pageSize, String search) {
        Page<BusinessDto> ExamineBusinessList = businessMapper.ExamineBusinessList(new Page<>(currentPage, pageSize), search);
        return Result.success(ExamineBusinessList);
    }

    //管理员-审核管理-不通过的商家申请
    public Result<?> FailBusinessList(int currentPage, int pageSize, String search) {
        Page<BusinessDto> FailBusinessList = businessMapper.FailBusinessList(new Page<>(currentPage, pageSize), search);
        return Result.success(FailBusinessList);
    }

    //管理员-用户管理-商家账号
    public Result<?> AllBusinessList(int currentPage, int pageSize, String search) {
        Page<BusinessDto> AllBusinessList = businessMapper.AllBusinessList(new Page<>(currentPage, pageSize), search);
        return Result.success(AllBusinessList);
    }

    //管理员-审核管理-未审核的商家申请界面-点击 通过 按钮——通过商家的申请
    public Result<?> PassBusiness(int bid, int shopid) {
        Business result1 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getShopid, shopid).eq(Business::getBid, bid));
        if (result1 != null) {
            String passBusinessSql = "update business set state=1 where bid=" + bid + " and shopid=" + shopid;       //商家账号0未审变成1通过
            String changeShopStatusSql = "update shop set status=2,bid=" + bid + " where shopid=" + shopid;           //该店铺状态变成2休息（让其他注册商家不能申请该店铺）并加上bid
            String failOtherBusinessSql = "update business set state=2 where bid<>" + bid + " and shopid=" + shopid; //申请这家店的其他商家审核状态改为2不通过
            jdbcTemplate.batchUpdate(passBusinessSql, changeShopStatusSql, failOtherBusinessSql);
            return Result.success();
        } else {
            return Result.error("-1", "操作失败，商家注册信息有变");
        }
    }

    //管理员-审核管理-未审核的商家申请 点击 不通过 按钮——不通过商家的申请
    public Result<?> FailBusiness(int bid, int shopid) {
        Business result1 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getShopid, shopid).eq(Business::getBid, bid));
        if (result1 != null) {
        businessMapper.FailBusiness(bid, shopid);
        return Result.success();
        } else {
            return Result.error("-1", "操作失败，商家注册信息有变");
        }
    }

    //管理员-审核管理-不通过的商家申请界面-点击 重新通过 按钮——重新通过商家的申请
    public Result<?> PassAgainBusiness(int bid, int shopid) {
        Business result1 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getShopid, shopid).eq(Business::getBid, bid));
        if (result1 != null) {
            Shop shopresult = shopMapper.selectOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getShopid, shopid));
            if (shopresult != null) {   //能根据传过来的店铺id查到有一个店铺
                if (shopresult.getBid() == 0 && shopresult.getStatus() == 3) {
                    String passBusinessSql = "update business set state=1 where bid=" + bid + " and shopid=" + shopid;       //商家账号0未审变成1通过
                    String changeShopStatusSql = "update shop set status=2,bid=" + bid + " where shopid=" + shopid;           //该店铺状态变成2休息（让其他注册商家不能申请该店铺）并加上bid
                    String failOtherBusinessSql = "update business set state=2 where bid<>" + bid + " and shopid=" + shopid; //申请这家店的其他商家审核状态改为2不通过
                    jdbcTemplate.batchUpdate(passBusinessSql, changeShopStatusSql, failOtherBusinessSql);
                    return Result.success();
                } else {
                    return Result.error("-1", "通过失败，该店铺已存在商家");
                }
            } else {
                return Result.error("-1", "通过失败，该店铺已经不存在");
            }
        } else {
            return Result.error("-1", "操作失败，商家注册信息有变");
        }
    }

    //不通过的商家申请界面-点击 删除 按钮——删除这条注册记录
    public Result<?> DeleteFailBusiness(int bid, int shopid) {
        Business result1 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getShopid, shopid).eq(Business::getBid, bid));
        if (result1 != null) {
        businessMapper.DeleteFailBusiness(bid);
        return Result.success();}
        else {
            return Result.error("-1", "操作失败，商家注册信息有变");
        }
    }

    //商家-个人信息 修改商家信息（手机号码）
    public Result<?> updateBusiness(Business business) {
        Business business2 = businessMapper.selectById(business.getBid());
        if (business2==null) {
            return Result.error("-1", "未找到用户");
        }else {
            Business result1 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getPhone, business.getPhone()));
            if (result1 == null) {
                businessMapper.updateById(business);
                return Result.success();
            } else {
                return Result.error("-1", "该手机号码已存在绑定");
            }
        }
    }

    //  商家-个人信息 修改密码
    public Result<?> updateBusinessPwd(Map<String, Object> map) {
        Business business = businessMapper.selectById((Integer)map.get("bid"));
        if (business == null) {
            return Result.error("-1", "未找到用户");
        }
        if (!map.get("password").toString().equals(business.getPassword())) {
            return Result.error("-1", "原密码错误");
        }
        if (map.get("password").toString().equals(map.get("newPass").toString())) {
            return Result.error("-1", "新密码与原密码相同");
        }
        map.put("newPass", map.get("newPass").toString());
        String sql = "update business set password='" + map.get("newPass").toString() + "' where bid = " + business.getBid();
        jdbcTemplate.update(sql);
        return Result.success();
    }

    //  管理员 登录（管理员只有一个账号 所以写入商家表）
    public Result<?> adminLogin(@RequestBody Business business) {    //用一个Business接收，∵实体类里面包括了前端传过来的账号和password
        Business result1 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getPhone, business.getPhone()));
        if (result1 != null) {  //存在
            if (result1.getPassword().equals(business.getPassword())) {  //密码正确
                if(result1.getShopid()>0){
                    return Result.error("-1","无管理员权限");
                }else {
                    return Result.success(result1);    //登陆成功返回 Result类的成功方法
                }
            } else {     //根据密码查出来为空——密码错误
                return Result.error("-1", "管理员密码错误");
            }
        } else {     //不存在
            return Result.error("-1", "管理员账号错误");
        }
    }

    //商家 登录
    public Result<?> businessLogin(@RequestBody Business business) {    //用一个Business接收，∵实体类里面包括了前端传过来的phone和password
        Business result1 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getPhone, business.getPhone()));
        if (result1 != null) {  //手机号码存在
            if (result1.getPassword().equals(business.getPassword())) {  //密码正确
                if (result1.getState() == 1) {
/*//                    设置token
                    String token = TokenUtils.genToken(String.valueOf(result1.getBid()), result1.getPassword());
                    business.setToken(token);*/
                    return Result.success(result1);    //登陆成功返回 Result类的成功方法
                } else if (result1.getState() == 0) {
                    return Result.error("-1", "该账号未审核，请等待管理员审核");
                } else {
                    return Result.error("-1", "该账号审核不通过，无法登录");
                }
            } else {     //根据密码查出来为空——密码错误
                return Result.error("-1", "密码错误");
            }
        } else {     //手机号码不存在
            return Result.error("-1", "该手机号码未注册账号");
        }
    }

    //商家  注册
    public Result<?> businessRegister(Business business) {
        System.out.println(business);
        //将传过来的对象的店铺编号 和 通过这个店铺编号查询(店铺表)得到的结果对比，不存在则返回空值
        Shop result1 = shopMapper.selectOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getShopid, business.getShopid()).eq(Shop::getStatus, 3));
        if (result1 == null) {      //商家表所申请的店铺编号b.shopid在店铺表中s.shopid不存在，或者该编号的店铺状态不是待租
            return Result.error("-1", "该店铺不存在或不为待租店铺");
        } else {      //存在要申请的shopid 且店铺 是待租状态
            //将传过来的对象的电话号码 和 通过这个号码查询到的结果对比，不存在则返回空值
            Business result2 = businessMapper.selectOne(Wrappers.<Business>lambdaQuery().eq(Business::getPhone, business.getPhone()));
            System.out.println(result2);
            if (result2 == null) {
                businessMapper.insert(business);   //将传过来的对象插入表中
                return Result.success("注册成功,请等待管理员审核");
            } else if (result2.getState() == 1) { // 传过来的电话号码在商家表中已存在 且 商家账号状态是1通过注册
                return Result.error("-1", "该手机号已注册店铺，不能申请多个");
            } else if (result2.getShopid() == business.getShopid() && result2.getState() == 0) {//该号码申请的店铺编号没变化
                return Result.error("-1", "该注册申请已存在,请等待管理员审核");
            } else if (result2.getShopid() != business.getShopid()) {     //该号码申请的店铺编号有改变
                business.setBid(result2.getBid());  //因为传过来的对象没有bid，先给他设置bid，updateById才能有效执行
                businessMapper.updateById(business);    //更新 商家 注册信息
                return Result.success("更改注册信息成功,请等待管理员审核");
            } else {
                return Result.error("-1", "注册申请审核不通过");
            }
        }
    }


}
