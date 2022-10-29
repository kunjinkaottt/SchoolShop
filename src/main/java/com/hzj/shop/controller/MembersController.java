package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.pojo.Members;
import com.hzj.shop.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MembersController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MembersService membersService;
    /*     获取page对象的查询结果
       System.out.println("当前页"+currentPage+",搜索内容"+search+"，搜索结果");
            AllMembers.getRecords().forEach(System.out::println);*/

    //管理员-用户管理-普通用户
    @GetMapping(value = {"/AllMembers/{currentPage}/{pageSize}/{search}", "/AllMembers/{currentPage}/{pageSize}"})
    public Result<?> AllMembers(@PathVariable("currentPage") Integer currentPage,    //pageNum第几页
                                @PathVariable("pageSize") Integer pageSize, // pageSize每页多少条记录
                                @PathVariable(value = "search", required = false) String search) {
        return membersService.allMembers(currentPage,pageSize,search);
    }

    //管理员-用户管理-普通用户  注销账号 操作
    @DeleteMapping("/DeleteMembers/{mid}")
    public Result<?> deleteMembers(@PathVariable("mid") Integer mid) {
        return membersService.deleteMembers(mid);
    }

    // 普通用户-个人信息管理 修改密码
    @PutMapping("/updateMemberPwd")
    public Result<?> updateMemberPwd(@RequestBody Map<String, Object> map) {
        return membersService.updateMemberPwd(map);
    }

    //普通用户-个人信息管理 修改普通用户信息（用户名+手机号码）
    @PutMapping("/updateMember")
    public Result<?> updateMember(@RequestBody Members member){
        return membersService.updateMember(member);
    }

    //普通用户 登录
    @PostMapping("/membersLogin")
    public Result<?> membersLogin(@RequestBody Members members){    //用一个members接收，∵实体类里面包括了前端传过来的phone和password
        return membersService.membersLogin(members);
    }

    //普通用户 注册
    @PostMapping("/membersRegister")
    public Result<?> membersRegister(@RequestBody Members members) {
        return membersService.membersRegister(members);
    }

}