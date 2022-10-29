package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.BusinessMapper;
import com.hzj.shop.mapper.ShopMapper;
import com.hzj.shop.pojo.Business;
import com.hzj.shop.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BusinessController {
    @Autowired
    BusinessMapper businessMapper;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    BusinessService businessService;

    //管理员-审核管理-未审核的商家申请
    @GetMapping(value = {"/ExamineList/{currentPage}/{pageSize}/{search}","/ExamineList/{currentPage}/{pageSize}"})   //2表+分页+模糊查询 商家和店铺列表（未审核的商家申请界面-在搜索框输入 手机号码 点击查询时触发）
    public Result<?> ExamineBusinessList(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                         @PathVariable("pageSize") Integer pageSize, //默认值 每页10条记录（ pageSize每页多少条记录）
                                         @PathVariable(value = "search",required = false) String search) {//required = false非必要的
        return businessService.ExamineBusinessList(currentPage,pageSize,search);
    }

    //管理员-审核管理-不通过的商家申请
    @GetMapping(value = {"/FailBusinessList/{currentPage}/{pageSize}/{search}","/FailBusinessList/{currentPage}/{pageSize}"}) //2表+分页+模糊查询 商家和店铺列表（不通过的商家申请界面-在搜索框输入 手机号码 点击查询时触发）
    public Result<?> FailBusinessList(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                                  @PathVariable("pageSize") Integer pageSize, //默认值 每页10条记录（ pageSize每页多少条记录）
                                                  @PathVariable(value = "search",required = false) String search) {//required = false非必要的
        return businessService.FailBusinessList(currentPage,pageSize,search);
    }

    //管理员-用户管理-商家账号
    @GetMapping(value = {"/AllBusinessList/{currentPage}/{pageSize}/{search}","/AllBusinessList/{currentPage}/{pageSize}"}) //2表+分页+模糊查询 所有商家和对应店铺列表（商家账号界面-在搜索框输入 手机号码 点击查询时触发）
    public Result<?> AllBusinessList(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                                  @PathVariable("pageSize") Integer pageSize, //默认值 每页10条记录（ pageSize每页多少条记录）
                                                  @PathVariable(value = "search",required = false) String search) {//required = false非必要的
        return businessService.AllBusinessList(currentPage,pageSize,search);
    }

    //未审核的商家申请界面-点击 通过 按钮——通过商家的申请
    @PutMapping("/PassBusiness/{bid}/{shopid}")
    public Result<?> PassBusiness(@PathVariable("bid") Integer bid,
                                  @PathVariable("shopid") Integer shopid) {
        return businessService.PassBusiness(bid,shopid);
    }

    //管理员-审核管理-未审核的商家申请 点击 不通过 按钮——不通过商家的申请
    @PutMapping("/FailBusiness/{bid}/{shopid}")
    public Result<?> FailBusiness(@PathVariable("bid") Integer bid,
                                  @PathVariable("shopid") Integer shopid) {
        return businessService.FailBusiness(bid,shopid);
    }

    //管理员-审核管理-不通过的商家申请界面-点击 重新通过 按钮——重新通过商家的申请
    @PutMapping("/PassAgainBusiness/{bid}/{shopid}")
    public Result<?> PassAgainBusiness(@PathVariable("bid") Integer bid,
                                       @PathVariable("shopid") Integer shopid) {
        return businessService.PassAgainBusiness(bid,shopid);
    }

    //不通过的商家申请界面-点击 删除 按钮——删除这条注册记录
    @DeleteMapping("/DeleteFailBusiness/{bid}/{shopid}")
    public Result<?> DeleteFailBusiness(@PathVariable("bid") Integer bid,
                                        @PathVariable("shopid") Integer shopid) {
        return businessService.DeleteFailBusiness(bid,shopid);
    }

    //商家-个人信息 修改商家信息（手机号码）
    @PutMapping("/updateBusiness")
    public Result<?> updateBusiness(@RequestBody Business business){
       return businessService.updateBusiness(business);
    }

    //商家-个人信息 修改密码
    @PutMapping("/updateBusinessPwd")
    public Result<?> updateBusinessPwd(@RequestBody Map<String, Object> map) {
        return businessService.updateBusinessPwd(map);
    }

//    管理员 登录（管理员只有一个账号 所以写入商家表）
    @PostMapping("/adminLogin")
    public Result<?> adminLogin(@RequestBody Business business){    //用一个Business接收，∵实体类里面包括了前端传过来的账号和password
        return businessService.adminLogin(business);
    }

    //商家 登录
    @PostMapping("/businessLogin")
    public Result<?> businessLogin(@RequestBody Business business){    //用一个Business接收，∵实体类里面包括了前端传过来的phone和password
        return businessService.businessLogin(business);
    }

    //商家  注册
    @PostMapping("/businessRegister")
    public Result<?> businessRegister(@RequestBody Business business){
        return businessService.businessRegister(business);
    }

}
