package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzj.shop.pojo.Staff;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository //代表持久层
public interface StaffMapper extends BaseMapper<Staff> {

//  商家-员工管理-员工信息 页面加载时执行
    List<Map<String, Object>> mystaffList(Integer shopid,String search);

//  商家-员工管理-员工信息  移除员工》相应普通用户申请表申请状态变成2不通过
    @Update("update apply set applystate=2 where mid=#{mid} and shopid =#{shopid}")
    void updateApplyState(Integer mid, Integer shopid);

}
