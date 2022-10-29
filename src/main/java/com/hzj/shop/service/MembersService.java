package com.hzj.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.MemberMapper;
import com.hzj.shop.pojo.Members;
import com.hzj.shop.pojo.dto.AllMembersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class MembersService {

    @Autowired
    MemberMapper memberMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //管理员-用户管理-普通用户
    public Result<?> allMembers(Integer currentPage, Integer pageSize, String search) {
        IPage<AllMembersDto> allMembers = memberMapper.allMembers(new Page<>(currentPage, pageSize), search);
        return Result.success(allMembers);
    }

    //管理员-用户管理-普通用户  注销账号 操作
    public Result<?> deleteMembers(Integer mid) {
        memberMapper.deleteById(mid);//评论表 收藏表 申请表 员工表都绑定了mid外键，一删全删
        return Result.success();
    }

    // 普通用户-个人信息管理 修改密码
    public Result<?> updateMemberPwd(Map<String, Object> map) {
        Members member = memberMapper.selectById((Integer) map.get("mid"));
        if (member == null) {
            return Result.error("-1", "未找到用户");
        }
        if (!map.get("password").toString().equals(member.getPassword())) {
            return Result.error("-1", "原密码错误");
        }
        if (map.get("password").toString().equals(map.get("newPass").toString())){
            return Result.error("-1", "新密码与原密码相同");
        }
        map.put("newPass", map.get("newPass").toString());
        String sql = "update members set password='" + map.get("newPass").toString() + "' where mid = " + member.getMid();
        jdbcTemplate.update(sql);
        return Result.success();
    }

    //普通用户-个人信息管理 修改普通用户信息（用户名+手机号码）
    public Result<?> updateMember(Members member){
        Members m = memberMapper.selectOne(Wrappers.<Members>lambdaQuery().eq(Members::getMid, member.getMid()));
        if (m==null){   //登录状态中 账号被管理员注销的情况
            return Result.error("-1","未找到用户");
        }
        Members result1 = memberMapper.selectOne(Wrappers.<Members>lambdaQuery().eq(Members::getPhone,member.getPhone()));//通过手机号查询
        if(result1==null){
            memberMapper.updateById(member);
            return Result.success("修改成功");
        }else {
            Members result2 = memberMapper.selectOne(Wrappers.<Members>lambdaQuery().eq(Members::getUsername,member.getUsername()).eq(Members::getMid,member.getMid()));
            if (result2==null){
                memberMapper.updateById(member);
                return Result.success("修改成功");
            }else {
                return Result.error("-1","该手机号码已存在绑定");
            }
        }
    }

    //普通用户 登录
    public Result<?> membersLogin(Members members){    //用一个members接收，∵实体类里面包括了前端传过来的phone和password
        Members result1 = memberMapper.selectOne(Wrappers.<Members>lambdaQuery().eq(Members::getPhone,members.getPhone()));
        System.out.println(result1);
        if(result1!=null){  //手机号码存在
            if(result1.getPassword().equals(members.getPassword())){  //密码正确
                return Result.success(result1);    //登陆成功返回 Result类的成功方法
            }else {     // 密码错误
                return Result.error("-1","密码错误");
            }
        }else {     //手机号码不存在
            return Result.error("-1","该手机号码未注册账号");
        }
    }

    //普通用户 注册
    public Result<?> membersRegister(Members members) {
        //将传过来的对象的电话号码 和 通过这个号码查询到的结果对比，存在则有值，不存在则为空
        Members result = memberMapper.selectOne(Wrappers.<Members>lambdaQuery().eq(Members::getPhone,members.getPhone()) );
        if(result!=null){   //这个号码在表里已经存在
            return Result.error("-1","该手机号码已经注册账号");
        }else{
            memberMapper.insert(members);   //传过来的对象插入表中
        }
        return Result.success();
    }

}
