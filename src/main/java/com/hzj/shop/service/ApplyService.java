package com.hzj.shop.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.ApplyMapper;
import com.hzj.shop.pojo.Apply;
import com.hzj.shop.pojo.dto.MemberApplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ApplyService extends ServiceImpl<ApplyMapper, Apply> {//继承之后 ServiceImpl<业务类对应的mapper接口,对应的实体类> 就可以使用mybatis-plus提供的方法了

    @Autowired
    ApplyMapper applyMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //商家-员工管理-应聘申请 获取shopid该店铺的所有应聘申请
    public Result<?> AllApplyList(int currenPage, int pageSize, int shopid) {
        Page<MemberApplyDto> memberApplyDtoPage = applyMapper.AllApplyList(new Page<>(currenPage, pageSize), shopid);
        return Result.success(memberApplyDtoPage);
    }

    //商家-员工管理-应聘申请 通过该mid的应聘申请
    public Result<?> Passapply(int mid, int shopid, int bid) {
        Apply apply = applyMapper.selectOne(Wrappers.<Apply>lambdaQuery().eq(Apply::getMid, mid).eq(Apply::getShopid, shopid));
        if (apply != null) {
            Date date = new Date();
            DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
            String formatDate = dFormat.format(date);
            String insertNewStaff = "insert into staff(mid,shopid,bid,create_time) values(" + mid + "," + shopid + "," + bid + ",'" + formatDate + "')";
            String updateApplyPass = "update apply set applystate=1 where mid=" + mid + " and shopid=" + shopid;
            jdbcTemplate.batchUpdate(insertNewStaff, updateApplyPass);
            return Result.success();
        } else {
            return Result.error("-1", "通过失败，该普通用户已取消申请");
        }
    }

    //商家-员工管理-应聘申请 不通过该mid的应聘申请
    public Result<?> Failapply(int mid, int shopid) {
        Apply apply = applyMapper.selectOne(Wrappers.<Apply>lambdaQuery().eq(Apply::getMid, mid).eq(Apply::getShopid, shopid));
        if (apply != null) {
            applyMapper.Failapply(mid, shopid);
            return Result.success();
        } else {
            return Result.error("-1", "操作失败，该普通用户已取消申请");
        }
    }

    //普通用户-店铺详情页面-点击 申请应聘 按钮
    public Result<?> Apply(Apply apply) {
        Apply result1 = applyMapper.selectOne(Wrappers.<Apply>lambdaQuery().eq(Apply::getMid, apply.getMid()).eq(Apply::getShopid, apply.getShopid()).eq(Apply::getApplystate, 0));
        if (result1 != null) {
            return Result.error("-1", "已申请应聘，请勿重复操作");
        } else {
            Apply result2 = applyMapper.selectOne(Wrappers.<Apply>lambdaQuery().eq(Apply::getMid, apply.getMid()).eq(Apply::getShopid, apply.getShopid()).eq(Apply::getApplystate, 2));
            if (result2 != null) {
                return Result.error("-1", "你的应聘申请不通过");
            } else {
                Date date = new Date();
                DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
                String formatDate = dFormat.format(date);
                apply.setApplytime(formatDate);

                System.out.println("getApplytime:"+apply.getApplytime());

                applyMapper.insert(apply);
                return Result.success();
            }
        }
    }

    //普通用户-应聘管理 ——传的参包括申请状态 applystate 只有0 1 2 处理中 通过 不通过
    public Result<?> MemberApplyList(int currentPage, int pageSize, int applystate, int mid, String search) {
        Page<MemberApplyDto> MemberApplyList = applyMapper.MemberApplyList(new Page<>(currentPage, pageSize), applystate, mid, search);
        return Result.success(MemberApplyList);
    }

    //  普通用户-应聘管理-不通过的申请 点击 删除 按钮
    public Result<?> DeleteFailApply(int applyid) {
        applyMapper.deleteById(applyid);
        return Result.success();
    }

    //    普通用户-应聘管理-处理中的申请 点击 取消申请 按钮
    public Result<?> cancelApply(int applyid) {
        Apply passApply = applyMapper.selectOne(Wrappers.<Apply>lambdaQuery().eq(Apply::getApplyid, applyid));
        if (passApply.getApplystate() == 1) {           //申请状态为1 通过
            return Result.error("-1", "操作失败，该申请已被商家通过");
        } else if (passApply.getApplystate() == 2) {  //申请状态为2 不通过
            return Result.error("-1", "操作失败，该申请已被商家拒绝");
        } else {     //状态为0 未处理
            applyMapper.deleteById(applyid);
            return Result.success();
        }
    }

}
