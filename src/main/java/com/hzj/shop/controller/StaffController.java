package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StaffController {

    @Autowired
    StaffService staffService;

    //商家-员工管理-员工信息    包括模糊查询
    @GetMapping(value = {"/MystaffList/{shopid}","/MystaffList/{shopid}/{search}"})
    public  List<Map<String, Object>> mystaffList(@PathVariable("shopid")Integer shopid,
                                                  @PathVariable(value = "search",required = false)String search){
        return staffService.mystaffList(shopid,search);
    }

    //商家-员工管理-员工信息  移除员工操作
    @DeleteMapping("/RemoveStaff/{staffid}/{mid}/{shopid}")
    public Result<?> removeStaff(@PathVariable("staffid")Integer staffid,
                                 @PathVariable("mid")Integer mid,
                                 @PathVariable("shopid")Integer shopid){
        return staffService.removeStaff(staffid,mid,shopid);
    }

    //普通用户-店铺详情页面  判断该普通用户是不是该店铺的员工
    @GetMapping("/MemberIsStaff/{mid}/{shopid}")
    public Result<?> memberIsStaff(@PathVariable("mid")Integer mid,
                                   @PathVariable("shopid")Integer shopid){
       return staffService.memberIsStaff(mid,shopid);
    }

}
