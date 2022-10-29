package com.hzj.shop.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.StaffMapper;
import com.hzj.shop.pojo.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class StaffService {

    @Autowired
    StaffMapper staffMapper;

    //商家-员工管理-员工信息
    public List<Map<String, Object>> mystaffList(Integer shopid,String search){
        List<Map<String, Object>> maps = staffMapper.mystaffList(shopid,search);
        System.out.println("mystaffList"+maps);
        return maps;
    }

    //商家-员工管理-员工信息  移除员工操作
    public Result<?> removeStaff(Integer staffid,Integer mid,Integer shopid){
        staffMapper.updateApplyState(mid,shopid);   // 相应普通用户申请表申请状态变成2不通过
        staffMapper.deleteById(staffid);            // 删除员工记录
        return Result.success();
    }

    //普通用户-店铺详情页面  判断该普通用户是不是该店铺的员工
    public Result<?> memberIsStaff(Integer mid,Integer shopid){
        Staff result = staffMapper.selectOne(Wrappers.<Staff>lambdaQuery().eq(Staff::getMid, mid).eq(Staff::getShopid, shopid));
        if(result!=null){
            return Result.success("你是该店铺的员工");
        }else {
            return Result.error("-1","不是员工");
        }
    }

}
